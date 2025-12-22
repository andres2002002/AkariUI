package com.akari.uicomponents.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Represents the different colors used by an [AkariCheckBox] in its various states.
 *
 * @property checkedBackgroundColor The background color of the checkbox when it is checked and enabled.
 * @property uncheckedBackgroundColor The background color of the checkbox when it is unchecked and enabled.
 * @property checkedBorderColor The border color of the checkbox when it is checked and enabled.
 * @property uncheckedBorderColor The border color of the checkbox when it is unchecked and enabled.
 * @property disabledCheckedBackgroundColor The background color of the checkbox when it is checked and disabled.
 * @property disabledUncheckedBackgroundColor The background color of the checkbox when it is unchecked and disabled.
 * @property disabledCheckedBorderColor The border color of the checkbox when it is checked and disabled.
 * @property disabledUncheckedBorderColor The border color of the checkbox when it is unchecked and disabled.
 * @property rippleColor The color used for the ripple effect when the checkbox is pressed.
 */
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