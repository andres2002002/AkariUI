package com.akari.uicomponents.reorderableComponents

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.zIndex

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> LazyItemScope.AkariReorderableItem(
    index: Int,
    state: AkariReorderableLazyState<T>,
    lazyListState: LazyListState,
    enabled: Boolean,
    dragActivation: DragActivation,
    isDragging: Boolean,
    content: @Composable AkariReorderableItemScope.() -> Unit
) {
    var itemHeight by remember { mutableIntStateOf(0) }

    // Mantener referencia actualizada del índice actual
    val currentIndex by rememberUpdatedState(index)
    val currentItemHeight by rememberUpdatedState(itemHeight)

    // Actualizar el modifier cuando cambie itemHeight
    val updatedDragHandleModifier = Modifier
        .pointerInput(dragActivation, enabled) {
            if (!enabled) return@pointerInput

            when (dragActivation) {
                DragActivation.Immediate -> {
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
                }
                DragActivation.LongPress -> {
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

    // Crear el scope que expone el drag handle
    val scope = remember(updatedDragHandleModifier) {
        AkariReorderableItemScope(updatedDragHandleModifier)
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
    ) {
        scope.content()
    }
}

@Composable
fun <T> AkariReorderableItem(
    index: Int,
    item: T,
    state: AkariReorderableColumnState<T>,
    enabled: Boolean,
    dragActivation: DragActivation,
    isDragging: Boolean,
    itemContent: @Composable AkariReorderableItemScope.(item: T, isDragging: Boolean) -> Unit
) {
    val currentIndex by rememberUpdatedState(index)

    val dragHandleModifier = Modifier.pointerInput(index, dragActivation, enabled) {
        if (!enabled) return@pointerInput

        when (dragActivation) {
            DragActivation.Immediate -> {
                detectDragGestures(
                    onDragStart = { state.startDragging(currentIndex) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.dragBy(dragAmount.y)
                        state.tryReorder()
                    },
                    onDragEnd = { state.stopDragging() },
                    onDragCancel = { state.stopDragging() }
                )
            }

            DragActivation.LongPress -> {
                detectDragGesturesAfterLongPress(
                    onDragStart = { state.startDragging(currentIndex) },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        state.dragBy(dragAmount.y)
                        state.tryReorder()
                    },
                    onDragEnd = { state.stopDragging() },
                    onDragCancel = { state.stopDragging() }
                )
            }
        }
    }

    val scope = remember(dragHandleModifier) {
        AkariReorderableItemScope(dragHandleModifier)
    }

    // Animación de la posición de los ítems no arrastrados
    val offsetY = remember { Animatable(0f) }

    LaunchedEffect(index, state.draggedIndex) {
        val draggedIdx = state.draggedIndex
        if (draggedIdx != null && index != draggedIdx) {
            // Los ítems que no se están arrastrando se mueven suavemente
            offsetY.animateTo(
                targetValue = 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            )
        }
    }

    Box(
        modifier = Modifier
            .onGloballyPositioned { coords ->
                val top = coords.positionInParent().y.toInt()
                val bottom = top + coords.size.height
                state.registerItemBounds(index, top, bottom)
            }
            .then(
                if (isDragging) {
                    Modifier
                        .zIndex(1f)
                        .graphicsLayer {
                            translationY = state.draggedOffsetY
                            shadowElevation = 16f
                            scaleX = 1.02f
                            scaleY = 1.02f
                            alpha = 0.95f
                        }
                } else {
                    Modifier
                        .zIndex(0f)
                        .graphicsLayer {
                            translationY = offsetY.value
                        }
                }
            )
    ) {
        scope.itemContent(item, isDragging)
    }
}