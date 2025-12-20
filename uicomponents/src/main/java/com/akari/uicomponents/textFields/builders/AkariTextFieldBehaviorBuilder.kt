package com.akari.uicomponents.textFields.builders

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.VisualTransformation
import com.akari.uicomponents.textFields.AkariLabelBehavior
import com.akari.uicomponents.textFields.config.AkariTextFieldBehavior

/**
 * A builder class for creating instances of [AkariTextFieldBehavior].
 *
 * This builder provides a fluent and mutable way to configure the behavioral properties
 * of an Akari text field, such as line limits, keyboard interactions, read-only state,
 * and label behavior.
 *
 * It can be initialized with a default [base] behavior, allowing for modification of
 * existing configurations.
 *
 * @property base The initial [AkariTextFieldBehavior] to copy values from. Defaults to a standard instance.
 * @property labelBehavior Defines how the label interacts with the input (e.g., always visible, auto-hiding).
 * @property readOnly If true, the text field is not editable but can still be focused and text can be selected.
 * @property singleLine If true, the text field becomes a single horizontally scrolling line.
 * @property maxLines The maximum height in terms of visible lines. Ignored if [singleLine] is true.
 * @property minLines The minimum height in terms of visible lines. Ignored if [singleLine] is true.
 * @property autoSelectOnFocus If true, all text within the field is selected when it receives focus.
 * @property requestFocus If true, the text field will request focus immediately upon composition.
 * @property keyboardOptions Software keyboard configuration options (e.g., capitalization, input type).
 * @property keyboardActions Callbacks to be executed when specific IME actions are triggered.
 * @property visualTransformation Filter for changing the visual output of the input (e.g., password masking).
 */
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