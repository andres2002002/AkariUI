package com.akari.uicomponents.checkbox

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
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
@Stable
data class AkariCheckBoxColors(
    val checkedCheckmarkColor: Color,
    val uncheckedCheckmarkColor: Color,
    val checkedBoxColor: Color,
    val uncheckedBoxColor: Color,
    val disabledCheckedBoxColor: Color,
    val disabledUncheckedBoxColor: Color,
    val checkedBorderColor: Color,
    val uncheckedBorderColor: Color,
    val disabledCheckedBorderColor: Color,
    val disabledUncheckedBorderColor: Color,
    val rippleColor: Color,
) {
    fun boxColor(enabled: Boolean, checked: Boolean): Color = when {
        !enabled -> if (checked) disabledCheckedBoxColor else disabledUncheckedBoxColor
        checked -> checkedBoxColor
        else -> uncheckedBoxColor
    }

    fun borderColor(enabled: Boolean, checked: Boolean): Color = when {
        !enabled -> if (checked) disabledCheckedBorderColor else disabledUncheckedBorderColor
        checked -> checkedBorderColor
        else -> uncheckedBorderColor
    }
}