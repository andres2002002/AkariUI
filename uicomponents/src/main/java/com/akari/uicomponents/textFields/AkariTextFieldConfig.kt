package com.akari.uicomponents.textFields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class AkariTextFieldConfig(
    val style: AkariTextFieldStyle,
    val behavior: AkariTextFieldBehavior,
    val label: (@Composable () -> Unit)?,
    val prefix: (@Composable () -> Unit)?,
    val suffix: (@Composable () -> Unit)?,
    val leadingIcon: (@Composable (Boolean) -> Unit)?,
    val trailingIcon: (@Composable (Boolean) -> Unit)?
)
