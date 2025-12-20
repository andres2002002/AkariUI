package com.akari.uicomponents.textFields.builders

import androidx.compose.runtime.Composable
import com.akari.uicomponents.textFields.config.AkariTextFieldSlots

class AkariTextFieldSlotsBuilder {
    var label: (@Composable () -> Unit)? = null
    var placeholder: (@Composable () -> Unit)? = null
    var prefix: (@Composable () -> Unit)? = null
    var suffix: (@Composable () -> Unit)? = null
    var supportingText: (@Composable () -> Unit)? = null
    var leadingIcon: (@Composable (Boolean) -> Unit)? = null
    var trailingIcon: (@Composable (Boolean) -> Unit)? = null

    internal fun build() = AkariTextFieldSlots(
        label = label,
        placeholder = placeholder,
        prefix = prefix,
        suffix = suffix,
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon
    )
}