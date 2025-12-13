package com.akari.uicomponents.textFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines the visual style for an Akari text field.
 *
 * This data class encapsulates various styling properties that can be customized for a text field component,
 * allowing for a consistent and configurable appearance.
 *
 * @property textStyle The [androidx.compose.ui.text.TextStyle] to be applied to the input text.
 * @property textFieldPadding The [AkariTextFieldPadding] to be applied to the text field's container.
 * @property shape The [androidx.compose.ui.graphics.Shape] of the text field's container. If null, a default shape may be used.
 * @property focusedBorderThickness The thickness of the border when the text field is in a focused state.
 * @property unfocusedBorderThickness The thickness of the border when the text field is in an unfocused state.
 * @property colors A [androidx.compose.material3.TextFieldColors] object defining the colors for different states (focused, unfocused, disabled, etc.).
 * @property cursorBrush The [androidx.compose.ui.graphics.Brush] to be used for painting the cursor. Defaults to a solid black color.
 */
class AkariTextFieldStyle (
    var textStyle: TextStyle? = null,
    var textFieldPadding: AkariTextFieldPadding = AkariTextFieldPadding(),
    var shape: Shape? = null,
    var focusedBorderThickness: Dp? = null,
    var unfocusedBorderThickness: Dp? = null,
    var colors: TextFieldColors? = null,
    var cursorBrush: Brush? = null
)