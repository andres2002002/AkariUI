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

/**
 * A wrapper component representing a single reorderable item within a list or column context.
 *
 * This composable handles the gesture detection (drag-and-drop), visual transformations (elevation,
 * scaling, z-index during drag), and communication with the reorderable state to update positions.
 * It provides a scope (`AkariReorderableItemScope`) to its content, allowing specific UI elements
 * (like a drag handle) to initiate the reordering process via the `dragHandleModifier`.
 *
 * There are two overloads of this function:
 * 1. For use within a Lazy List context (requires `LazyItemScope`).
 * 2. For use within a generic Column context.
 *
 * @param T The type of the data item being displayed.
 * @param index The current index of this item in the list.
 * @param state The state object managing the reordering logic (either [AkariReorderableLazyState] or [AkariReorderableColumnState]).
 * @param enabled Whether reordering gestures are enabled for this item.
 * @param dragActivation Defines how the drag gesture is initiated (e.g., [DragActivation.Immediate] or [DragActivation.LongPress]).
 * @param isDragging Boolean flag indicating if this specific item is currently being dragged.
 * @param content The UI content of the item. This lambda provides an [AkariReorderableItemScope] which exposes
 * access to the `dragHandleModifier` for applying drag gestures to specific child components.
 */
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

    val itemModifier = remember(isDragging) {
        if (isDragging) {
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
    }

    Box(
        modifier = itemModifier
            .onSizeChanged { itemHeight = it.height }
    ) {
        scope.content()
    }
}

/**
 * A composable wrapper that makes an individual item reorderable within a list or column context.
 *
 * There are two implementations of this function:
 * 1. An extension on [LazyItemScope] optimized for Lazy lists using [AkariReorderableLazyState].
 * 2. A standalone composable optimized for standard Columns using [AkariReorderableColumnState].
 *
 * The wrapper handles drag gesture detection based on the specified [DragActivation] strategy,
 * manages z-index changes during dragging, applies translation/elevation effects, and exposes
 * a specific scope [AkariReorderableItemScope] to apply the drag handle modifier to a specific
 * UI element within the content.
 *
 * @param T The type of the data item being displayed.
 * @param index The current index of this item in the data collection.
 * @param state The state object managing the reordering logic (either [AkariReorderableLazyState] or [AkariReorderableColumnState]).
 * @param item The data object representing this item (only for the Column version).
 * @param enabled Whether drag-and-drop reordering is currently enabled for this item.
 * @param dragActivation Defines how the drag gesture is initiated (e.g., [DragActivation.Immediate] or [DragActivation.LongPress]).
 * @param isDragging Boolean flag indicating if this specific item is currently being dragged.
 * @param itemContent The composable content of the item.
 */
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