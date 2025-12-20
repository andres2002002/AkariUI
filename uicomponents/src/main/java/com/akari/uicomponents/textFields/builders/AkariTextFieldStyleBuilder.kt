package com.akari.uicomponents.textFields.builders

import androidx.compose.material3.TextFieldColors
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.akari.uicomponents.textFields.config.AkariTextFieldStyle

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