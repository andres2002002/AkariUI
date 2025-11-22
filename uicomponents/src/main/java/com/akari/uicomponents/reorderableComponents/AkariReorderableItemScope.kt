package com.akari.uicomponents.reorderableComponents

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
class AkariReorderableItemScope(
    private val dragModifier: Modifier
) {
    /**
     * Aplica este modifier al composable que actuará como "handle" de arrastre.
     * Típicamente un ícono de tres líneas horizontales.
     */
    fun Modifier.dragHandle(): Modifier = this.then(dragModifier)
}