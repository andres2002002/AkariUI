package com.akari.uicomponents.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

// Clase de datos para colores personalizables
@Immutable
data class AkariCheckBoxColors(
    val checkedBackgroundColor: Color,
    val uncheckedBackgroundColor: Color,
    val checkedBorderColor: Color,
    val uncheckedBorderColor: Color,
    val disabledCheckedBackgroundColor: Color,
    val disabledUncheckedBackgroundColor: Color,
    val disabledCheckedBorderColor: Color,
    val disabledUncheckedBorderColor: Color,
    val rippleColor: Color
) {
    fun disabledBackgroundColor(checked: Boolean): Color =
        if (checked) disabledCheckedBackgroundColor else disabledUncheckedBackgroundColor

    fun disabledBorderColor(checked: Boolean): Color =
        if (checked) disabledCheckedBorderColor else disabledUncheckedBorderColor
}