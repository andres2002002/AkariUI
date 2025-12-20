package com.akari.uicomponents.textFields.config

import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

/**
 * Defines the visual style for an Akari text field.
 *
 * This data class encapsulates various styling properties that can be customized for a text field component,
 * allowing for a consistent and configurable appearance.
 *
 * @property textStyle The [TextStyle] to be applied to the input text.
 * @property shape The [Shape] of the text field's container. If null, a default shape may be used.
 * @property focusedBorderThickness The thickness of the border when the text field is in a focused state.
 * @property unfocusedBorderThickness The thickness of the border when the text field is in an unfocused state.
 * @property colors A [TextFieldColors] object defining the colors for different states (focused, unfocused, disabled, etc.).
 * @property cursorBrush The [Brush] to be used for painting the cursor. Defaults to a solid black color.
 */
@Immutable
data class AkariTextFieldStyle (
    val textStyle: TextStyle? = null,
    val shape: Shape? = null,
    val focusedBorderThickness: Dp? = null,
    val unfocusedBorderThickness: Dp? = null,
    val colors: TextFieldColors? = null,
    val cursorBrush: Brush? = null
)