package com.akari.uicomponents.reorderableComponents

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class AkariReorderableState<T>(
    private val onMove: (from: Int, to: Int) -> Unit
) {
    var draggedIndex by mutableStateOf<Int?>(null)
        private set

    // Separar el offset para que solo el ítem arrastrado lo lea
    private var _draggedOffsetY = mutableFloatStateOf(0f)
    val draggedOffsetY: Float get() = _draggedOffsetY.floatValue

    private var draggedItemSize: Int = 0

    // Callbacks para haptic feedback
    internal var onDragStart: (() -> Unit)? = null
    internal var onReorder: (() -> Unit)? = null
    internal var onDragEnd: (() -> Unit)? = null

    fun startDragging(index: Int, itemSize: Int) {
        draggedIndex = index
        _draggedOffsetY.floatValue = 0f
        draggedItemSize = itemSize
        onDragStart?.invoke()
    }

    fun dragBy(delta: Float) {
        _draggedOffsetY.floatValue += delta
    }

    fun stopDragging() {
        if (draggedIndex != null) {
            onDragEnd?.invoke()
        }
        draggedIndex = null
        _draggedOffsetY.floatValue = 0f
        draggedItemSize = 0
    }
    fun tryReorder(layoutInfo: LazyListLayoutInfo) {
        val from = draggedIndex ?: return
        val visibleItems = layoutInfo.visibleItemsInfo

        // Evitar allocations: usar find directamente sin filter
        var draggedItem: LazyListItemInfo? = null
        for (item in visibleItems) {
            if (item.index == from) {
                draggedItem = item
                break
            }
        }
        if (draggedItem == null) return

        val midPoint = draggedItem.offset + _draggedOffsetY.floatValue + draggedItem.size / 2f

        // Buscar target sin crear listas intermedias
        var targetIndex: Int? = null
        var targetSize: Int = 0
        for (item in visibleItems) {
            if (item.index != from) {
                val start = item.offset.toFloat()
                val end = (item.offset + item.size).toFloat()
                if (midPoint in start..end) {
                    targetIndex = item.index
                    targetSize = item.size
                    break
                }
            }
        }

        val target = targetIndex ?: return

        if (target != from) {
            onMove(from, target)
            onReorder?.invoke()

            val offsetAdjustment = if (target > from) {
                -targetSize.toFloat()
            } else {
                targetSize.toFloat()
            }
            _draggedOffsetY.floatValue += offsetAdjustment
            draggedIndex = target
        }
    }
}