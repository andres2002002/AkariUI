package com.akari.uicomponents.reorderableComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

/**
 * Creates and remembers a [AkariReorderableLazyState] instance.
 *
 * This is the recommended way to create a [AkariReorderableLazyState] in a composable function.
 * It uses [remember] to ensure that the same state instance is retained across recompositions.
 *
 * @param T The type of the items in the list.
 * @param onMove A lambda that will be invoked when an item is moved. It provides the `from`
 * index (the original position of the item) and the `to` index (the new position of the item).
 * This lambda should be used to update your underlying data source (e.g., a list) to reflect the reorder.
 * @param onDragStart An optional lambda that will be invoked when the drag gesture starts.
 * @param onDragEnd An optional lambda that will be invoked when the drag gesture ends.
 * @return A remembered [AkariReorderableLazyState] instance.
 */
@Composable
fun <T> rememberAkariReorderableLazyState(
    onMove: (from: Int, to: Int) -> Unit,
    onDragStart: (() -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null
): AkariReorderableLazyState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    val currentOnDragStart by rememberUpdatedState(onDragStart)
    val currentOnDragEnd by rememberUpdatedState(onDragEnd)
    return remember {
        AkariReorderableLazyState(
            onDragStart = currentOnDragStart,
            onDragEnd = currentOnDragEnd,
            onMove = currentOnMove
        )
    }
}

/**
 * Creates and remembers a [AkariReorderableLazyState] instance.
 *
 * This is the recommended way to create a [AkariReorderableLazyState] in a composable function.
 * It uses [remember] to ensure that the same state instance is retained across recompositions.
 *
 * @param T The type of the items in the list.
 * @param onMove A lambda that will be invoked when an item is moved. It provides the `from`
 * index (the original position of the item) and the `to` index (the new position of the item).
 * This lambda should be used to update your underlying data source (e.g., a list) to reflect the reorder.
 * @return A remembered [AkariReorderableLazyState] instance.
 */
@Composable
fun <T> rememberAkariReorderableLazyState(
    onMove: (from: Int, to: Int) -> Unit,
): AkariReorderableLazyState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    return remember {
        AkariReorderableLazyState(
            onMove = currentOnMove
        )
    }
}

/**
 * Creates and remembers a [AkariReorderableColumnState] instance.
 *
 * This is the recommended way to create a [AkariReorderableColumnState] in a composable function.
 * It uses [remember] to ensure that the same state instance is retained across recompositions.
 *
 * @param T The type of the items in the list.
 * @param onMove A lambda that will be invoked when an item is moved. It provides the `from`
 * index (the original position of the item) and the `to` index (the new position of the item).
 * This lambda should be used to update your underlying data source (e.g., a list) to reflect the reorder.
 * @param onDragStart An optional lambda that will be invoked when the drag gesture starts.
 * @param onDragEnd An optional lambda that will be invoked when the drag gesture ends.
 * @return A remembered [AkariReorderableColumnState] instance.
 */
@Composable
fun <T> rememberAkariReorderableColumnState(
    onMove: (from: Int, to: Int) -> Unit,
    onDragStart: (() -> Unit)? = null,
    onDragEnd: (() -> Unit)? = null
): AkariReorderableColumnState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    val currentOnDragStart by rememberUpdatedState(onDragStart)
    val currentOnDragEnd by rememberUpdatedState(onDragEnd)
    return remember { AkariReorderableColumnState(
        onMove = currentOnMove,
        onDragStart = currentOnDragStart,
        onDragEnd = currentOnDragEnd
    ) }
}

/**
 * Creates and remembers a [AkariReorderableColumnState] instance.
 *
 * This is the recommended way to create a [AkariReorderableColumnState] in a composable function.
 * It uses [remember] to ensure that the same state instance is retained across recompositions.
 *
 * @param T The type of the items in the list.
 * @param onMove A lambda that will be invoked when an item is moved. It provides the `from`
 * index (the original position of the item) and the `to` index (the new position of the item).
 * This lambda should be used to update your underlying data source (e.g., a list) to reflect the reorder.
 * @return A remembered [AkariReorderableColumnState] instance.
 */
@Composable
fun <T> rememberAkariReorderableColumnState(
    onMove: (from: Int, to: Int) -> Unit,
): AkariReorderableColumnState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    return remember { AkariReorderableColumnState(
        onMove = currentOnMove
    ) }
}