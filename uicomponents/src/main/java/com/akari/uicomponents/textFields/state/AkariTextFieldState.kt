package com.akari.uicomponents.textFields.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusProperties
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import com.akari.uicomponents.textFields.AkariLabelBehavior
import com.akari.uicomponents.textFields.AkariTextFieldBehavior
import com.akari.uicomponents.textFields.AkariTextFieldStyle

/**
 * Represents the state and configuration for an Akari text field.
 *
 * This data class encapsulates all the parameters required to define the appearance, behavior,
 * and content of a text field, making it easy to manage and pass around in a Compose UI.
 * It follows a state-hoisting pattern, where the state is owned by a higher-level composable.
 *
 * @param value The [TextFieldValue] to be shown in the text field.
 * @param onValueChange The callback that is triggered when the input service updates the text. An
 *   updated [TextFieldValue] comes as a parameter of the callback.
 * @param style Defines the visual styling of the text field. See [AkariTextFieldStyle].
 * @param behavior Defines the behavioral properties of the text field. See [AkariTextFieldBehavior].
 * @param label The optional label to be displayed inside or outside the text field.
 * @param labelBehavior Determines whether the label is displayed inside or outside the text field.
 * @param placeholder The optional placeholder to be displayed when the text field is in focus and
 *   the input text is empty.
 * @param prefix The optional leading composable to be displayed at the beginning of the text field
 *   container.
 * @param suffix The optional trailing composable to be displayed at the end of the text field
 *   container.
 * @param isError Indicates if the text field's current value is in an error state. If set to true,
 *   the supporting text and visual elements will be displayed in an error color.
 * @param supportingText The optional supporting text to be displayed below the text field. This
 *   can be used for hints or error messages.
 * @param leadingIcon The optional leading icon to be displayed at the beginning of the text field
 *   container. The `Boolean` parameter indicates if the text field is focused.
 * @param trailingIcon The optional trailing icon to be displayed at the end of the text field
 *   container. The `Boolean` parameter indicates if the text field is focused.
 *  @param focusRequester Optional [FocusRequester] to control the focus state of the text field.
 *  @param focusProperties Additional properties to be applied when requesting focus.
 *
 */
data class AkariTextFieldState(
    val value: TextFieldValue,
    val onValueChange: (TextFieldValue) -> Unit,
    val style: AkariTextFieldStyle = AkariTextFieldStyle(),
    val behavior: AkariTextFieldBehavior = AkariTextFieldBehavior(),
    val label: (@Composable () -> Unit)? = null,
    val labelBehavior: AkariLabelBehavior = AkariLabelBehavior.EXTERNAL,
    val placeholder: (@Composable () -> Unit)? = null,
    val prefix: (@Composable () -> Unit)? = null,
    val suffix: (@Composable () -> Unit)? = null,
    val isError: Boolean = false,
    val supportingText: @Composable (() -> Unit)? = null,
    val leadingIcon: @Composable ((Boolean) -> Unit)? = null,
    val trailingIcon: @Composable ((Boolean) -> Unit)? = null,
    val focusRequester: FocusRequester? = null,
    val focusProperties: FocusProperties.() -> Unit = {},
    val onTextLayout: (TextLayoutResult) -> Unit = {}
){
    init {
        require(behavior.maxLines >= behavior.minLines) {
            "maxLines debe ser mayor o igual que minLines"
        }
    }
    val valueText: String get() = value.text
    val isEmpty: Boolean get() = valueText.isEmpty()
    val isBlank: Boolean get() = valueText.isBlank()

    fun updateValue(newText: String): AkariTextFieldState {
        return copy(value = value.copy(text = newText))
    }

    fun clearValue(): AkariTextFieldState {
        return copy(value = TextFieldValue(""))
    }
}