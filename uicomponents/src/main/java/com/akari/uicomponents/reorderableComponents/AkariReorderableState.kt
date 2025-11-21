package com.akari.uicomponents.reorderableComponents

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

    var draggedOffsetY by mutableFloatStateOf(0f)
        private set

    // Guardamos el tamaño del ítem arrastrado para cálculos precisos
    private var draggedItemSize: Int = 0

    fun startDragging(index: Int, itemSize: Int) {
        draggedIndex = index
        draggedOffsetY = 0f
        draggedItemSize = itemSize
    }

    fun dragBy(delta: Float) {
        draggedOffsetY += delta
    }

    fun stopDragging() {
        draggedIndex = null
        draggedOffsetY = 0f
        draggedItemSize = 0
    }

    fun tryReorder(layoutInfo: LazyListLayoutInfo) {
        val from = draggedIndex ?: return
        val draggedItem = layoutInfo.visibleItemsInfo
            .firstOrNull { it.index == from } ?: return

        val midPoint = draggedItem.offset + draggedOffsetY + draggedItem.size / 2f

        val target = layoutInfo.visibleItemsInfo
            .filter { it.index != from }
            .firstOrNull { info ->
                midPoint in info.offset.toFloat()..(info.offset + info.size).toFloat()
            }?.index ?: return

        if (target != from) {
            // Calcular el offset del ítem objetivo para ajuste preciso
            val targetItem = layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == target } ?: return

            onMove(from, target)

            // Ajustar offset basado en el tamaño real del ítem destino
            val offsetAdjustment = if (target > from) {
                -targetItem.size.toFloat()
            } else {
                targetItem.size.toFloat()
            }
            draggedOffsetY += offsetAdjustment
            draggedIndex = target
        }
    }
}