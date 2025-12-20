package com.akari.uicomponents.textFields.builders

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.VisualTransformation
import com.akari.uicomponents.textFields.AkariLabelBehavior
import com.akari.uicomponents.textFields.config.AkariTextFieldBehavior

class AkariTextFieldBehaviorBuilder(private val base: AkariTextFieldBehavior = AkariTextFieldBehavior()) {
    var labelBehavior: AkariLabelBehavior = base.labelBehavior
    var readOnly: Boolean = base.readOnly
    var singleLine: Boolean = base.singleLine
    var maxLines: Int = base.maxLines
    var minLines: Int = base.minLines
    var autoSelectOnFocus: Boolean = base.autoSelectOnFocus
    var requestFocus: Boolean = base.requestFocus
    var keyboardOptions: KeyboardOptions = base.keyboardOptions
    var keyboardActions: KeyboardActions = base.keyboardActions
    var visualTransformation: VisualTransformation = base.visualTransformation

    fun build() = AkariTextFieldBehavior(
        labelBehavior = labelBehavior,
        readOnly = readOnly,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        autoSelectOnFocus = autoSelectOnFocus,
        requestFocus = requestFocus,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation
    )
}