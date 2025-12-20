package com.akari.uicomponents.textFields.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusProperties

@Immutable
data class AkariTextFieldConfig(
    val style: AkariTextFieldStyle = AkariTextFieldStyle(),
    val padding: AkariTextFieldPadding = AkariTextFieldPadding(),
    val behavior: AkariTextFieldBehavior = AkariTextFieldBehavior(),
    val slots: AkariTextFieldSlots = AkariTextFieldSlots(),
    val focusProperties: FocusProperties.() -> Unit = {}
)