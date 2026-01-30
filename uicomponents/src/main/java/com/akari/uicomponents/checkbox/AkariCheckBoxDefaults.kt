package com.akari.uicomponents.checkbox

import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion

/**
 * Contains default values used by [AkariCheckBox].
 */
@Immutable
object AkariCheckBoxDefaults {
    @Composable
    fun colors(
        checkedBoxColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedBoxColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        checkedCheckmarkColor: Color = MaterialTheme.colorScheme.onPrimary,
        uncheckedCheckmarkColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        disabledCheckedBoxColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
        disabledUncheckedBoxColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f),
        checkedBorderColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedBorderColor: Color = MaterialTheme.colorScheme.outline,
        disabledCheckedBorderColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
        disabledUncheckedBorderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.38f),
        rippleColor: Color = MaterialTheme.colorScheme.primary
    ): AkariCheckBoxColors = AkariCheckBoxColors(
        checkedCheckmarkColor = checkedCheckmarkColor,
        uncheckedCheckmarkColor = uncheckedCheckmarkColor,
        checkedBoxColor = checkedBoxColor,
        uncheckedBoxColor = uncheckedBoxColor,
        disabledCheckedBoxColor = disabledCheckedBoxColor,
        disabledUncheckedBoxColor = disabledUncheckedBoxColor,
        checkedBorderColor = checkedBorderColor,
        uncheckedBorderColor = uncheckedBorderColor,
        disabledCheckedBorderColor = disabledCheckedBorderColor,
        disabledUncheckedBorderColor = disabledUncheckedBorderColor,
        rippleColor = rippleColor
    )
}