package com.akari.uicomponents.reorderableComponents

import android.util.Log
import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListLayoutInfo
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * A state object that can be hoisted to control and observe reordering gestures.
 *
 * This state is required by the `reorderable` modifier and the `ReorderableItem` composable
 * to coordinate the drag-and-drop behavior. It holds information about the currently dragged item,
 * its offset, and handles the logic for initiating, updating, and finalizing the reordering process.
 *
 * @param T The type of the items in the list.
 * @param onMove A callback invoked when an item is moved to a new position. It provides the `from`
 * and `to` indices of the move. This is where you should update your underlying list data.
 */
@Stable
class AkariReorderableLazyState<T>(
    // Callback que notifica al consumidor que un ítem debe moverse
    private val onMove: (from: Int, to: Int) -> Unit,

    // Callbacks públicos opcionales para eventos de drag
    private val onDragStart: (() -> Unit)? = null,
    private val onDragEnd: (() -> Unit)? = null
) {

    // Índice del ítem que se está arrastrando actualmente.
    // null significa que no hay drag activo.
    var draggedIndex by mutableStateOf<Int?>(null)
        private set

    /*
     * Offset vertical acumulado del drag (en píxeles).
     * Se separa en una MutableFloatState para que
     * SOLO el ítem arrastrado observe este valor y
     * evitar recomposiciones innecesarias del resto.
     */
    private var _draggedOffsetY = mutableFloatStateOf(0f)

    // Exposición de solo lectura del offset
    val draggedOffsetY: Float
        get() = _draggedOffsetY.floatValue

    // Tamaño (alto) del ítem que se está arrastrando.
    // Se usa para ajustar el offset tras un reorder.
    private var draggedItemSize: Int = 0

    /*
     * Callbacks internos.
     * Normalmente se usan para:
     * - Haptic feedback
     * - Sonidos
     * - Animaciones internas desacopladas del consumidor
     */
    internal var onInternalDragStart: (() -> Unit)? = null
    internal var onInternalReorder: (() -> Unit)? = null
    internal var onInternalDragEnd: (() -> Unit)? = null

    /**
     * Inicializa el estado de drag.
     * Se llama cuando el usuario empieza a arrastrar un ítem.
     */
    fun startDragging(index: Int, itemSize: Int) {
        draggedIndex = index
        _draggedOffsetY.floatValue = 0f
        Log.d("AkariReorderableLazyState", "startDragging: index = $index, size = $itemSize")
        draggedItemSize = itemSize

        // Eventos internos (ej. vibración)
        onInternalDragStart?.invoke()

        // Eventos públicos (ej. analytics, UI state)
        onDragStart?.invoke()
    }

    /**
     * Actualiza el offset vertical en función del desplazamiento del dedo.
     * delta suele venir directamente del PointerInput.
     */
    fun dragBy(delta: Float) {
        _draggedOffsetY.floatValue += delta
        Log.d("AkariReorderableLazyState", "dragBy: delta = $delta")
    }

    /**
     * Finaliza el drag y limpia el estado.
     */
    fun stopDragging() {
        if (draggedIndex != null) {
            onInternalDragEnd?.invoke()
            onDragEnd?.invoke()
        }

        // Reset completo del estado
        draggedIndex = null
        _draggedOffsetY.floatValue = 0f
        Log.d("AkariReorderableLazyState", "stopDragging")
        draggedItemSize = 0
    }

    /**
     * Intenta realizar un reorder en base a la posición actual del drag.
     *
     * Se llama normalmente durante el drag para comprobar
     * si el ítem cruzó el centro de otro ítem visible.
     */
    fun tryReorder(layoutInfo: LazyListLayoutInfo) {
        val from = draggedIndex ?: return
        val visibleItems = layoutInfo.visibleItemsInfo

        val draggedItem = visibleItems.firstOrNull { it.index == from } ?: return

        val draggedCenter =
            draggedItem.offset +
                    draggedItem.size / 2f +
                    _draggedOffsetY.floatValue

        val clampedCenter = draggedCenter.coerceIn(
            layoutInfo.viewportStartOffset.toFloat(),
            layoutInfo.viewportEndOffset.toFloat()
        )

        for (item in visibleItems) {
            if (item.index == from) continue

            val targetCenter = item.offset + item.size / 2f

            val shouldSwap =
                (from < item.index && clampedCenter > targetCenter) ||
                        (from > item.index && clampedCenter < targetCenter)

            if (shouldSwap) {
                onMove(from, item.index)
                onInternalReorder?.invoke()

                val direction = if (item.index > from) -1 else 1
                _draggedOffsetY.floatValue += direction * item.size

                draggedIndex = item.index
                break
            }
        }
    }
}