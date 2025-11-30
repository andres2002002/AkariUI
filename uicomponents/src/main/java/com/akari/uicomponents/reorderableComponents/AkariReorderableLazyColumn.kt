package com.akari.uicomponents.reorderableComponents

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

/**
 * A `LazyColumn` composable that enables its items to be reordered via drag-and-drop.
 *
 * This component builds upon `LazyColumn` and integrates with `AkariReorderableState` to manage
 * the drag-and-drop state, including the current dragged item, its offset, and the reordering logic.
 * It also features haptic feedback and automatic scrolling when an item is dragged near the edges
 * of the viewport.
 *
 * @param T The type of the items in the list.
 * @param items The list of items to display.
 * @param state The state object that manages the reordering process, created via [rememberAkariReorderableLazyState].
 * @param modifier The modifier to be applied to the `LazyColumn`.
 * @param enabled A boolean to enable or disable the reordering functionality. When `false`, drag gestures are ignored. Defaults to `true`.
 * @param enableHapticFeedback If `true`, provides haptic feedback during drag events (start, reorder, end). Defaults to `true`.
 * @param dragActivation Specifies how a drag gesture is initiated, either immediately or after a long press. See [DragActivation]. Defaults to [DragActivation.LongPress].
 * @param lazyListState The state object to be used by the underlying `LazyColumn`. Defaults to a new state created by `rememberLazyListState`.
 * @param key A factory of stable and unique keys representing the item. Using keys allows Compose to uniquely identify items, which is crucial for performance and correctness in lists.
 * @param itemContent The composable content for each item in the list. The lambda receives an `AkariReorderableItemScope`, the `item` itself, and a `isDragging` boolean.
 * You must use the `Modifier.akariDragHandle()` from the scope on the element that should initiate the drag.
 *
 * @see AkariReorderableLazyState
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AkariReorderableLazyColumn(
    items: List<T>,
    state: AkariReorderableLazyState<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enableHapticFeedback: Boolean = true,
    dragActivation: DragActivation = DragActivation.LongPress,
    lazyListState: LazyListState = rememberLazyListState(),
    key: (T) -> Any,
    itemContent: @Composable AkariReorderableItemScope.(item: T, isDragging: Boolean) -> Unit
) {
    val isDragging by remember {
        derivedStateOf { state.draggedIndex != null }
    }

    // Configurar haptic feedback
    val view = LocalView.current

    LaunchedEffect(enableHapticFeedback) {
        if (enableHapticFeedback) {
            state.onInternalDragStart = {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
            state.onInternalReorder = {
                view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            }
            state.onInternalDragEnd = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                } else {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                }
            }
        } else {
            state.onInternalDragStart = null
            state.onInternalReorder = null
            state.onInternalDragEnd = null
        }
    }

    // Auto-scroll optimizado: solo corre cuando hay drag activo
    if (isDragging) {
        AutoScrollEffect(state, lazyListState)
    }

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> key(item) }
        ) { index, item ->
            // Derivar isDragging por ítem para minimizar recomposiciones
            val isItemDragging by remember(index) {
                derivedStateOf { state.draggedIndex == index }
            }

            AkariReorderableItem(
                index = index,
                state = state,
                lazyListState = lazyListState,
                enabled = enabled,
                dragActivation = dragActivation,
                isDragging = isItemDragging
            ) {
                itemContent(item, isItemDragging)
            }
        }
    }
}

@Composable
private fun AutoScrollEffect(
    state: AkariReorderableLazyState<*>,
    lazyListState: LazyListState
) {
    LaunchedEffect(Unit) {
        while (true) {
            val draggedIdx = state.draggedIndex
            if (draggedIdx != null) {
                val layoutInfo = lazyListState.layoutInfo
                val draggedItem = layoutInfo.visibleItemsInfo
                    .find { it.index == draggedIdx }

                if (draggedItem != null) {
                    val itemCenter = draggedItem.offset + state.draggedOffsetY + draggedItem.size / 2f
                    val viewportStart = layoutInfo.viewportStartOffset
                    val viewportEnd = layoutInfo.viewportEndOffset
                    val scrollThreshold = 100f

                    // Scroll proporcional a la cercanía al borde
                    val scrollAmount = when {
                        itemCenter < viewportStart + scrollThreshold -> {
                            val proximity = 1f - (itemCenter - viewportStart) / scrollThreshold
                            -15f * proximity.coerceIn(0f, 1f)
                        }
                        itemCenter > viewportEnd - scrollThreshold -> {
                            val proximity = 1f - (viewportEnd - itemCenter) / scrollThreshold
                            15f * proximity.coerceIn(0f, 1f)
                        }
                        else -> 0f
                    }

                    if (scrollAmount != 0f) {
                        lazyListState.scrollBy(scrollAmount)
                    }
                }
            }
            withFrameNanos { }
        }
    }
}
