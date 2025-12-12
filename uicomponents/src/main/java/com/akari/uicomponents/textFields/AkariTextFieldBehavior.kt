package com.akari.uicomponents.textFields

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.VisualTransformation

/**
 * Defines the behavior and interaction configuration for an Akari text field.
 *
 * This data class encapsulates various behavioral properties that control how a text field functions,
 * such as its enabled state, keyboard options, and line limits.
 *
 * @param enabled Controls the enabled state of the text field. When `false`, the text field cannot be
 *                focused or edited.
 * @param readOnly Controls the editable state of the text field. When `true`, the text field can be
 *                 focused and its content can be selected, but it cannot be modified.
 * @param singleLine When `true`, this text field becomes a single horizontally scrolling text field
 *                   instead of wrapping onto multiple lines.
 * @param maxLines The maximum number of lines to be displayed.
 * @param minLines The minimum number of lines to be displayed.
 * @param autoSelectOnFocus When `true`, the entire text content will be selected automatically when
 *                          the text field gains focus.
 * @param requestFocus When `true`, a focus request will be sent for this text field.
 * @param keyboardOptions Software keyboard options that instruct the keyboard on how to behave,
 *                        e.g., keyboard type and IME action.
 * @param keyboardActions Actions to be triggered in response to IME (Input Method Editor) actions
 *                        from the software keyboard.
 * @param visualTransformation Transforms the visual representation of the input text, such as for
 *                             password fields.
 */
class AkariTextFieldBehavior(
    var enabled: Boolean = true,
    var readOnly: Boolean = false,
    var singleLine: Boolean = false,
    var maxLines: Int = Int.MAX_VALUE,
    var minLines: Int = 1,
    var autoSelectOnFocus: Boolean = false,
    var requestFocus: Boolean = false,
    var keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    var keyboardActions: KeyboardActions = KeyboardActions.Default,
    var visualTransformation: VisualTransformation = VisualTransformation.None
) {
    fun validateValues(){
        if (singleLine) {
            minLines = 1
            maxLines = 1
        }
        if (minLines <= 0) minLines = 1
        if (maxLines <= 0) maxLines = minLines
        if (minLines > maxLines) maxLines = minLines
    }
}