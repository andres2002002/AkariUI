package com.akari.uicomponents.textFields.builders

import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.akari.uicomponents.textFields.config.AkariTextFieldStyle

/**
 * A builder class for creating customized instances of [AkariTextFieldStyle].
 *
 * This builder allows for the step-by-step construction of a text field style configuration.
 * It provides mutable properties for all style attributes, enabling the modification of specific
 * aspects of a style while keeping others at their defaults or inheriting them from a base style.
 *
 * Usage example:
 * ```kotlin
 * val customStyle = AkariTextFieldStyleBuilder().apply {
 *     shape = RoundedCornerShape(8.dp)
 *     focusedBorderThickness = 2.dp
 * }.build()
 * ```
 *
 * @property textStyle The typography style to be applied to the text input.
 * @property shape The shape of the text field's container (e.g., rounded corners).
 * @property focusedBorderThickness The thickness of the border when the text field is focused.
 * @property unfocusedBorderThickness The thickness of the border when the text field is not focused.
 * @property colors The [TextFieldColors] configuration defining the color palette for various states.
 * @property cursorBrush The brush used to draw the cursor.
 * @param base An optional existing [AkariTextFieldStyle] to use as a starting point. Defaults to a standard empty style.
 */
class AkariTextFieldStyleBuilder(private val base: AkariTextFieldStyle = AkariTextFieldStyle()) {
    var textStyle: TextStyle? = base.textStyle
    var shape: Shape? = base.shape
    var focusedBorderThickness: Dp? = base.focusedBorderThickness
    var unfocusedBorderThickness: Dp? = base.unfocusedBorderThickness
    var colors: TextFieldColors? = base.colors
    var cursorBrush: Brush? = base.cursorBrush

    fun build() = AkariTextFieldStyle(
        textStyle = textStyle,
        shape = shape,
        focusedBorderThickness = focusedBorderThickness,
        unfocusedBorderThickness = unfocusedBorderThickness,
        colors = colors,
        cursorBrush = cursorBrush
    )
}