package com.akari.uicomponents.textFields.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusProperties
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.TextFieldValue
import com.akari.uicomponents.textFields.AkariLabelBehavior
import com.akari.uicomponents.textFields.AkariTextFieldVariant
import com.akari.uicomponents.textFields.AkariTextFieldBehavior
import com.akari.uicomponents.textFields.AkariTextFieldPadding
import com.akari.uicomponents.textFields.AkariTextFieldStyle

/**
 * A builder with an enhanced DSL for declaratively creating an [AkariTextFieldState].
 *
 * This builder provides a fluent and readable way to configure all the properties
 * of a text field's state, including its style, behavior, and associated UI elements
 * like labels, icons, and supporting text.
 *
 * @param value The initial [TextFieldValue] of the text field.
 * @param onValueChange A callback that is invoked when the [TextFieldValue] changes.
 */
class AkariTextFieldStateBuilder internal constructor(
    private val value: TextFieldValue,
    private val onValueChange: (TextFieldValue) -> Unit
) {
    private var style: AkariTextFieldStyle = AkariTextFieldStyle()
    private var behavior: AkariTextFieldBehavior = AkariTextFieldBehavior()
    var label: (@Composable () -> Unit)? = null
    var labelBehavior: AkariLabelBehavior = AkariLabelBehavior.EXTERNAL
    var placeholder: (@Composable () -> Unit)? = null
    var prefix: (@Composable () -> Unit)? = null
    var suffix: (@Composable () -> Unit)? = null
    var isError: Boolean = false
    var supportingText: (@Composable () -> Unit)? = null
    var leadingIcon: (@Composable (Boolean) -> Unit)? = null
    var trailingIcon: (@Composable (Boolean) -> Unit)? = null
    var focusRequester: FocusRequester? = null
    var focusProperties: FocusProperties.() -> Unit = {}
    var onTextLayout: (TextLayoutResult) -> Unit = {}

    fun variant(variant: () -> AkariTextFieldVariant) {
        style = variant().style
        behavior = variant().behavior
    }

    /**
     * DSL para configurar el estilo del TextField
     */
    fun style(block: AkariTextFieldStyle.() -> Unit) {
        style.apply(block)
    }

    /**
     * DSL para configurar el comportamiento del TextField
     */
    fun behavior(block: AkariTextFieldBehavior.() -> Unit) {
        behavior.apply(block)
    }

    /**
     * DSL para configurar el padding del TextField
     */
    fun textFieldPadding(block: AkariTextFieldPadding.() -> Unit) {
        style.textFieldPadding.apply(block)
    }

    internal fun build(): AkariTextFieldState {
        behavior.validateValues()
        return AkariTextFieldState(
            value = value,
            onValueChange = onValueChange,
            style = style,
            behavior = behavior,
            label = label,
            labelBehavior = labelBehavior,
            placeholder = placeholder,
            prefix = prefix,
            suffix = suffix,
            isError = isError,
            supportingText = supportingText,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            focusRequester = focusRequester,
            focusProperties = focusProperties,
            onTextLayout = onTextLayout
        )
    }
}