package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.textFields.AkariTextFieldStyle
import com.akari.uicomponents.textFields.AkariTextFieldVariant

// Para temas consistentes en toda la app
sealed class AkariTextFieldTheme {
    abstract val outlined: AkariTextFieldVariant
    abstract val filled: AkariTextFieldVariant
    @Composable
    abstract fun colors(): TextFieldColors
    @Composable
    abstract fun shape(): Shape

    @Composable
    abstract fun cursorBrush(): Brush

    data object Material : AkariTextFieldTheme() {
        override val outlined = AkariTextFieldVariant.Outlined
        override val filled = AkariTextFieldVariant.Filled
        @Composable
        override fun colors(): TextFieldColors = OutlinedTextFieldDefaults.colors()
        @Composable
        override fun shape(): Shape = OutlinedTextFieldDefaults.shape
        @Composable
        override fun cursorBrush(): Brush =
            SolidColor(OutlinedTextFieldDefaults.colors().cursorColor)

    }

    data object Cupertino : AkariTextFieldTheme() {
        override val outlined = AkariTextFieldVariant.Custom(
            style = AkariTextFieldStyle(
                contentPadding = PaddingValues(12.dp),
                borderThickness = 1.dp,
                shape = RoundedCornerShape(8.dp)
            )
        )
        override val filled = AkariTextFieldVariant.Custom(
            style = AkariTextFieldStyle(
                contentPadding = PaddingValues(12.dp),
                borderThickness = 0.dp,
                shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
            )
        )
        @Composable
        override fun colors(): TextFieldColors = OutlinedTextFieldDefaults.colors()
        @Composable
        override fun shape(): Shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
        @Composable
        override fun cursorBrush(): Brush =
            SolidColor(OutlinedTextFieldDefaults.colors().cursorColor)
    }
}