package com.akari.uicomponents.checkbox

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AkariCheckBoxDefaults {
    @Composable
    fun colors(
        checkedBackgroundColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        checkedBorderColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor: Color = MaterialTheme.colorScheme.outline,
        disabledCheckedBackgroundColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
        disabledUncheckedBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
        disabledCheckedBorderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
        disabledUncheckedBorderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
        rippleColor: Color = MaterialTheme.colorScheme.primary
    ): AkariCheckBoxColors = AkariCheckBoxColors(
        checkedBackgroundColor = checkedBackgroundColor,
        uncheckedBackgroundColor = uncheckedBackgroundColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedBorderColor = uncheckedBorderColor,
        disabledCheckedBackgroundColor = disabledCheckedBackgroundColor,
        disabledUncheckedBackgroundColor = disabledUncheckedBackgroundColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        rippleColor = rippleColor
    )
}