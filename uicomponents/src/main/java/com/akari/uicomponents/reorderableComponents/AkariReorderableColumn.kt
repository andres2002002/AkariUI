package com.akari.uicomponents.reorderableComponents

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

/**
 * A `Column` composable that enables its items to be reordered via drag-and-drop.
 *
 * This component builds upon `Column` and integrates with `AkariReorderableState` to manage
 * the drag-and-drop state, including the current dragged item, its offset, and the reordering logic.
 * It also features haptic feedback.
 *
 * @param T The type of the items in the list.
 * @param items The list of items to display.
 * @param state The state object that manages the reordering process, created via [rememberAkariReorderableLazyState].
 * @param modifier The modifier to be applied to the `LazyColumn`.
 * @param enabled A boolean to enable or disable the reordering functionality. When `false`, drag gestures are ignored. Defaults to `true`.
 * @param enableHapticFeedback If `true`, provides haptic feedback during drag events (start, reorder, end). Defaults to `true`.
 * @param dragActivation Specifies how a drag gesture is initiated, either immediately or after a long press. See [DragActivation]. Defaults to [DragActivation.LongPress].
 * @param verticalArrangement The vertical arrangement of the children. Defaults to [Arrangement.Top].
 * @param horizontalAlignment The horizontal alignment of the children. Defaults to [androidx.compose.ui.Alignment.Start].
 * @param itemContent The composable content for each item in the list. The lambda receives an `AkariReorderableItemScope`, the `item` itself, and a `isDragging` boolean.
 * You must use the `Modifier.akariDragHandle()` from the scope on the element that should initiate the drag.
 *
 * @see AkariReorderableLazyState
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AkariReorderableColumn(
    items: List<T>,
    state: AkariReorderableColumnState<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enableHapticFeedback: Boolean = true,
    dragActivation: DragActivation = DragActivation.LongPress,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: androidx.compose.ui.Alignment.Horizontal = androidx.compose.ui.Alignment.Start,
    itemContent: @Composable AkariReorderableItemScope.(item: T, isDragging: Boolean) -> Unit
) {
    val view = LocalView.current

    // Configurar haptic feedback
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

    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        items.forEachIndexed { index, item ->
            val isItemDragging by remember(index) {
                derivedStateOf { state.draggedIndex == index }
            }

            AkariReorderableItem(
                index = index,
                item = item,
                state = state,
                enabled = enabled,
                dragActivation = dragActivation,
                isDragging = isItemDragging,
                itemContent = itemContent
            )
        }
    }
}