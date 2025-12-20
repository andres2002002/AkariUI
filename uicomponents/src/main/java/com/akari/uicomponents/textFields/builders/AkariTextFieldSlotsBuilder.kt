package com.akari.uicomponents.textFields.builders

import androidx.compose.runtime.Composable
import com.akari.uicomponents.textFields.config.AkariTextFieldSlots
import com.akari.uicomponents.textFields.AkariTextField

/**
 * A builder class for configuring the composable slots of an [AkariTextField].
 *
 * This builder follows a DSL style to allow convenient assignment of optional composable content
 * such as labels, placeholders, prefixes, suffixes, supporting text, and icons.
 *
 * Usage example:
 * ```
 * val slots = AkariTextFieldSlotsBuilder().apply {
 *     label = { Text("Username") }
 *     leadingIcon = { isError -> Icon(Icons.Default.Person, contentDescription = null) }
 *     supportingText = { Text("Enter your unique ID") }
 *}.build
 *
 * ```
 *
 * @property label A composable for the label text, typically displayed inside or above the text field.
 * @property placeholder A composable displayed when the input is empty and not focused.
 * @property prefix A composable displayed before the input text (e.g., currency symbol).
 * @property suffix A composable displayed after the input text (e.g., unit of measure).
 * @property supportingText A composable for helper or error text displayed below the text field.
 * @property leadingIcon A composable for an icon at the start of the field. Accepts a boolean indicating error state.
 * @property trailingIcon A composable for an icon at the end of the field (e.g., clear button). Accepts a boolean indicating error state.
 * @see AkariTextFieldSlots
 */
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