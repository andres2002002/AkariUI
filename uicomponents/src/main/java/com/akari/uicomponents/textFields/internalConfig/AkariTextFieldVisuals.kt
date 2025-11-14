package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

class AkariTextFieldVisuals(
    private val colors: TextFieldColors,
    private val state: State,
    private val animationSpec: AnimationSpec<Color> = tween(durationMillis = 150)
){

    // Enums en lugar de sealed classes para mayor eficiencia
    enum class Component {
        BORDER, BACKGROUND, HINT, PLACEHOLDER, TEXT, SUPPORTING
    }

    enum class State {
        UNFOCUSED, FOCUSED, ERROR, DISABLED
    }

    /**
     * Obtiene el color animado para un componente.
     * Anima suavemente las transiciones entre estados.
     */
    @Composable
    fun animatedColor(
        component: Component,
        animationSpec: AnimationSpec<Color> = this.animationSpec
    ): androidx.compose.runtime.State<Color> {
        val targetColor = getColorForComponent(component, state)
        return animateColorAsState(
            targetValue = targetColor,
            animationSpec = animationSpec,
            label = "TextField${component.name}Color"
        )
    }

    /**
     * Obtiene el color reactivo para un componente basado en el estado actual.
     * Se recompone automáticamente cuando cambia el estado.
     */
    @Composable
    fun color(component: Component): androidx.compose.runtime.State<Color> {
        return remember(component) {
            derivedStateOf {
                getColorForComponent(component, state)
            }
        }
    }

    // Lazy initialization para construir el mapa solo cuando se necesite
    private val visualColors: Map<Component, Map<State, Color>> by lazy {
        buildColorMap()
    }

    private fun buildColorMap(): Map<Component, Map<State, Color>> = mapOf(
        Component.BORDER to mapOf(
            State.UNFOCUSED to colors.unfocusedIndicatorColor,
            State.FOCUSED to colors.focusedIndicatorColor,
            State.ERROR to colors.errorIndicatorColor,
            State.DISABLED to colors.disabledIndicatorColor
        ),
        Component.BACKGROUND to mapOf(
            State.UNFOCUSED to colors.unfocusedContainerColor,
            State.FOCUSED to colors.focusedContainerColor,
            State.ERROR to colors.errorContainerColor,
            State.DISABLED to colors.disabledContainerColor
        ),
        Component.HINT to mapOf(
            State.UNFOCUSED to colors.unfocusedTextColor,
            State.FOCUSED to colors.focusedTextColor,
            State.ERROR to colors.errorTextColor,
            State.DISABLED to colors.disabledTextColor
        ),
        Component.PLACEHOLDER to mapOf(
            State.UNFOCUSED to colors.unfocusedPlaceholderColor,
            State.FOCUSED to colors.focusedPlaceholderColor,
            State.ERROR to colors.errorPlaceholderColor,
            State.DISABLED to colors.disabledPlaceholderColor
        ),
        Component.TEXT to mapOf(
            State.UNFOCUSED to colors.unfocusedTextColor,
            State.FOCUSED to colors.focusedTextColor,
            State.ERROR to colors.errorTextColor,
            State.DISABLED to colors.disabledTextColor
        ),
        Component.SUPPORTING to mapOf(
            State.UNFOCUSED to colors.unfocusedSupportingTextColor,
            State.FOCUSED to colors.focusedSupportingTextColor,
            State.ERROR to colors.errorSupportingTextColor,
            State.DISABLED to colors.disabledSupportingTextColor
        )
    )

    // Operador invoke para sintaxis más limpia: visuals(Component.BORDER)
    operator fun invoke(component: Component): Color =
        getColorForComponent(component, state)

    private fun getColorForComponent(component: Component, state: State): Color =
        visualColors[component]?.get(state)?: colors.unfocusedIndicatorColor
}