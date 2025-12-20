package com.akari.uicomponents.textFields.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
class AkariTextFieldSlots(
    val label: (@Composable () -> Unit)? = null,
    val placeholder: (@Composable () -> Unit)? = null,
    val prefix: (@Composable () -> Unit)? = null,
    val suffix: (@Composable () -> Unit)? = null,
    val supportingText: (@Composable () -> Unit)? = null,
    val leadingIcon: (@Composable (Boolean) -> Unit)? = null,
    val trailingIcon: (@Composable (Boolean) -> Unit)? = null
)
