package com.akari.uicomponents.reorderableComponents

import androidx.annotation.FloatRange
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.awt.font.NumericShaper


@Stable
class AkariReorderableColumnState<T>(
    private val onMove: (from: Int, to: Int) -> Unit
) {
    var draggedIndex by mutableStateOf<Int?>(null)
        private set

    private var _draggedOffsetY = mutableFloatStateOf(0f)
    val draggedOffsetY: Float get() = _draggedOffsetY.floatValue

    private val itemBounds = mutableStateMapOf<Int, IntRange>()

    // Callbacks para haptic feedback
    internal var onDragStart: (() -> Unit)? = null
    internal var onReorder: (() -> Unit)? = null
    internal var onDragEnd: (() -> Unit)? = null

    fun registerItemBounds(index: Int, top: Int, bottom: Int) {
        itemBounds[index] = top..bottom
    }

    fun startDragging(index: Int) {
        draggedIndex = index
        _draggedOffsetY.floatValue = 0f
        onDragStart?.invoke()
    }

    fun dragBy(deltaY: Float) {
        _draggedOffsetY.floatValue += deltaY
    }

    fun stopDragging() {
        if (draggedIndex != null) {
            onDragEnd?.invoke()
        }
        draggedIndex = null
        _draggedOffsetY.floatValue = 0f
    }

    fun tryReorder() {
        val from = draggedIndex ?: return
        val currentRange = itemBounds[from] ?: return

        val centerY = currentRange.first + (currentRange.last - currentRange.first) / 2f + _draggedOffsetY.floatValue

        // Buscar sin allocations
        var targetIndex: Int? = null
        for (entry in itemBounds.entries) {
            if (entry.key != from) {
                val range = entry.value
                if (centerY >= range.first.toFloat() && centerY <= range.last.toFloat()) {
                    targetIndex = entry.key
                    break
                }
            }
        }

        val target = targetIndex ?: return

        if (target != from) {
            onMove(from, target)
            onReorder?.invoke()
            draggedIndex = target

            // Ajustar offset para evitar saltos
            val fromRange = itemBounds[from] ?: return
            val targetRange = itemBounds[target] ?: return
            val fromHeight = fromRange.last - fromRange.first
            val targetHeight = targetRange.last - targetRange.first

            val offsetAdjustment = if (target > from) {
                -targetHeight.toFloat()
            } else {
                fromHeight.toFloat()
            }
            _draggedOffsetY.floatValue += offsetAdjustment
        }
    }
}
