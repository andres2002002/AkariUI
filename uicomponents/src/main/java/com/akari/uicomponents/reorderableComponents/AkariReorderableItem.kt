package com.akari.uicomponents.reorderableComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.zIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> LazyItemScope.AkariReorderableItem(
    index: Int,
    state: AkariReorderableState<T>,
    lazyListState: LazyListState,
    enabled: Boolean,
    dragActivation: DragActivation,
    isDragging: Boolean,
    content: @Composable () -> Unit
) {
    var itemHeight by remember { mutableIntStateOf(0) }

    // Mantener referencia actualizada del índice actual
    val currentIndex by rememberUpdatedState(index)
    val currentItemHeight by rememberUpdatedState(itemHeight)

    val dragModifier = if (!enabled) {
        Modifier
    } else {
        Modifier.pointerInput( dragActivation) {
            if (dragActivation == DragActivation.Immediate) {
                detectDragGestures(
                    onDragStart = { state.startDragging(currentIndex, currentItemHeight) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.dragBy(dragAmount.y)
                        state.tryReorder(lazyListState.layoutInfo)
                    },
                    onDragEnd = { state.stopDragging() },
                    onDragCancel = { state.stopDragging() }
                )
            } else {
                detectDragGesturesAfterLongPress(
                    onDragStart = { state.startDragging(currentIndex, currentItemHeight) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.dragBy(dragAmount.y)
                        state.tryReorder(lazyListState.layoutInfo)
                    },
                    onDragEnd = { state.stopDragging() },
                    onDragCancel = { state.stopDragging() }
                )
            }
        }
    }


    // NO usar animateItem() en el ítem que se arrastra
    val itemModifier = if (isDragging) {
        Modifier
            .zIndex(1f)
            .graphicsLayer {
                translationY = state.draggedOffsetY
                shadowElevation = 16f
                scaleX = 1.03f
                scaleY = 1.03f
            }
    } else {
        Modifier
            .zIndex(0f)
            .animateItem() // Solo animar ítems que NO se arrastran
    }

    Box(
        modifier = itemModifier
            .onSizeChanged { itemHeight = it.height }
            .then(dragModifier)
    ) {
        content()
    }
}