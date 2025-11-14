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
 * @property contentPadding The padding applied to the inner content of the text field.
 * @property minHeightInnerTextField The minimum height for the core text field area.
 * @property shape The [androidx.compose.ui.graphics.Shape] of the text field's container. If null, a default shape may be used.
 * @property borderThickness The thickness of the border when drawn. If null, no border might be drawn or a default thickness is used.
 * @property colors A [androidx.compose.material3.TextFieldColors] object defining the colors for different states (focused, unfocused, disabled, etc.).
 * @property cursorBrush The [androidx.compose.ui.graphics.Brush] to be used for painting the cursor. Defaults to a solid black color.
 */
class AkariTextFieldStyle (
    var textStyle: TextStyle = TextStyle.Default,
    var contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    var minHeightInnerTextField: Dp = 24.dp,
    var shape: Shape? = null,
    var borderThickness: Dp? = null,
    var colors: TextFieldColors? = null,
    var cursorBrush: Brush? = null
){
    val minHeightTextField: Dp = minHeightInnerTextField + 16.dp
}