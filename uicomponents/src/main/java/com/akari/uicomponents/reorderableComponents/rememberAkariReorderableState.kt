package com.akari.uicomponents.reorderableComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState

@Composable
fun <T> rememberAkariReorderableState(
    onMove: (from: Int, to: Int) -> Unit
): AkariReorderableState<T> {
    val currentOnMove by rememberUpdatedState(onMove)
    return remember {
        AkariReorderableState { from, to -> currentOnMove(from, to) }
    }
}