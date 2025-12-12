package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.State

class AkariTextFieldVisuals(
    private val colors: TextFieldColors,
    private val visualState: VisualState,
    private val animationSpec: AnimationSpec<Color> = tween(durationMillis = 150)
){

    // Enums en lugar de sealed classes para mayor eficiencia
    enum class Component {
        BORDER,
        HINT,
        PLACEHOLDER,
        TEXT,
        CURSOR,
        CONTAINER,
        LEADING,
        TRAILING,
        PREFIX,
        SUFFIX,
        SUPPORTING,
        LABEL;
        companion object {
            val SIZE = entries.size
        }
    }

    enum class VisualState {
        UNFOCUSED, FOCUSED, ERROR, DISABLED;

        companion object {
            val SIZE = entries.size
        }
    }

    // Array 2D para acceso O(1) garantizado: [component][state]
    // Más eficiente que Map<Component, Map<State, Color>>
    private val colorMatrix: Array<Array<Color>> by lazy {
        Array(Component.SIZE) { componentIndex ->
            Array(VisualState.SIZE) { stateIndex ->
                resolveColor(Component.entries[componentIndex], VisualState.entries[stateIndex])
            }
        }
    }

    @Composable
    fun animatedColor(
        component: Component,
        animationSpec: AnimationSpec<Color> = this.animationSpec
    ): State<Color> {
        val targetColor = getColor(component)
        return animateColorAsState(
            targetValue = targetColor,
            animationSpec = animationSpec,
            label = "TextField${component.name}Color"
        )
    }

    /**
     * Obtiene el color estático para un componente (sin animación).
     * Inline para mejor performance.
     */
    @Composable
    fun color(component: Component): Color {
        return remember(component, visualState) {
            getColor(component)
        }
    }

    /**
     * Acceso directo al color usando array indexing.
     * Inline para eliminar overhead.
     */
    fun getColor(component: Component): Color {
        return colorMatrix[component.ordinal][visualState.ordinal]
    }

    /**
     * Operador invoke para sintaxis limpia: visuals(Component.BORDER)
     */
    operator fun invoke(component: Component): Color = getColor(component)

    /**
     * Resuelve el color apropiado desde TextFieldColors.
     * Esta función solo se ejecuta una vez por componente/estado durante la inicialización lazy.
     */
    private fun resolveColor(component: Component, state: VisualState): Color {
        return when (component) {
            Component.BORDER -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedIndicatorColor
                VisualState.FOCUSED -> colors.focusedIndicatorColor
                VisualState.ERROR -> colors.errorIndicatorColor
                VisualState.DISABLED -> colors.disabledIndicatorColor
            }
            Component.CONTAINER -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedContainerColor
                VisualState.FOCUSED -> colors.focusedContainerColor
                VisualState.ERROR -> colors.errorContainerColor
                VisualState.DISABLED -> colors.disabledContainerColor
            }
            Component.TEXT -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedTextColor
                VisualState.FOCUSED -> colors.focusedTextColor
                VisualState.ERROR -> colors.errorTextColor
                VisualState.DISABLED -> colors.disabledTextColor
            }
            Component.PLACEHOLDER -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedPlaceholderColor
                VisualState.FOCUSED -> colors.focusedPlaceholderColor
                VisualState.ERROR -> colors.errorPlaceholderColor
                VisualState.DISABLED -> colors.disabledPlaceholderColor
            }
            Component.LABEL -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedLabelColor
                VisualState.FOCUSED -> colors.focusedLabelColor
                VisualState.ERROR -> colors.errorLabelColor
                VisualState.DISABLED -> colors.disabledLabelColor
            }
            Component.SUPPORTING -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedSupportingTextColor
                VisualState.FOCUSED -> colors.focusedSupportingTextColor
                VisualState.ERROR -> colors.errorSupportingTextColor
                VisualState.DISABLED -> colors.disabledSupportingTextColor
            }
            Component.CURSOR -> when (state) {
                VisualState.UNFOCUSED -> colors.cursorColor
                VisualState.FOCUSED -> colors.cursorColor
                VisualState.ERROR -> colors.errorCursorColor
                VisualState.DISABLED -> colors.cursorColor
            }
            Component.LEADING -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedLeadingIconColor
                VisualState.FOCUSED -> colors.focusedLeadingIconColor
                VisualState.ERROR -> colors.errorLeadingIconColor
                VisualState.DISABLED -> colors.disabledLeadingIconColor
            }
            Component.TRAILING -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedTrailingIconColor
                VisualState.FOCUSED -> colors.focusedTrailingIconColor
                VisualState.ERROR -> colors.errorTrailingIconColor
                VisualState.DISABLED -> colors.disabledTrailingIconColor
            }
            Component.PREFIX -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedPrefixColor
                VisualState.FOCUSED -> colors.focusedPrefixColor
                VisualState.ERROR -> colors.errorPrefixColor
                VisualState.DISABLED -> colors.disabledPrefixColor
            }
            Component.SUFFIX -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedSuffixColor
                VisualState.FOCUSED -> colors.focusedSuffixColor
                VisualState.ERROR -> colors.errorSuffixColor
                VisualState.DISABLED -> colors.disabledSuffixColor
            }
            Component.HINT -> when (state) {
                VisualState.UNFOCUSED -> colors.unfocusedTextColor
                VisualState.FOCUSED -> colors.focusedTextColor
                VisualState.ERROR -> colors.errorTextColor
                VisualState.DISABLED -> colors.disabledTextColor
            }
        }
    }
}