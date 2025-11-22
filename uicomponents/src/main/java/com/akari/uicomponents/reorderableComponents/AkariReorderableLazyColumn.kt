package com.akari.uicomponents.reorderableComponents

import android.os.Build
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AkariReorderableLazyColumn(
    items: List<T>,
    state: AkariReorderableState<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    enableHapticFeedback: Boolean = true,
    dragActivation: DragActivation = DragActivation.Immediate,
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
            state.onDragStart = {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
            }
            state.onReorder = {
                view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
            }
            state.onDragEnd = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    view.performHapticFeedback(HapticFeedbackConstants.GESTURE_END)
                } else {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                }
            }
        } else {
            state.onDragStart = null
            state.onReorder = null
            state.onDragEnd = null
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
    state: AkariReorderableState<*>,
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
