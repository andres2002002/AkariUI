package com.akari.uicomponents.reorderableComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

/**
 * Creates and remembers a [AkariReorderableState] instance.
 *
 * This is the recommended way to create a [AkariReorderableState] in a composable function.
 * It uses [remember] to ensure that the same state instance is retained across recompositions.
 *
 * @param T The type of the items in the list.
 * @param onMove A lambda that will be invoked when an item is moved. It provides the `from`
 * index (the original position of the item) and the `to` index (the new position of the item).
 * This lambda should be used to update your underlying data source (e.g., a list) to reflect the reorder.
 * @return A remembered [AkariReorderableState] instance.
 */
@Composable
fun <T> rememberAkariReorderableState(
    onMove: (from: Int, to: Int) -> Unit
): AkariReorderableState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    return remember {
        AkariReorderableState { from, to -> currentOnMove(from, to) }
    }
}