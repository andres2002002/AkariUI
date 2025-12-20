package com.akari.uicomponents.textFields.config

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.input.VisualTransformation
import com.akari.uicomponents.textFields.AkariLabelBehavior

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
@Immutable
data class AkariTextFieldBehavior(
    val labelBehavior: AkariLabelBehavior = AkariLabelBehavior.FLOATING,
    val readOnly: Boolean = false,
    val singleLine: Boolean = false,
    val maxLines: Int = Int.MAX_VALUE,
    val minLines: Int = 1,
    val autoSelectOnFocus: Boolean = false,
    val requestFocus: Boolean = false,
    val keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    val keyboardActions: KeyboardActions = KeyboardActions.Default,
    val visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val normalizedMinLines: Int get() = if (singleLine) 1 else minLines.coerceAtLeast(1)
    val normalizedMaxLines: Int get() = if (singleLine) 1 else maxLines.coerceAtLeast(normalizedMinLines)
}