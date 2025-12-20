package com.akari.uicomponents.textFields.builders

import androidx.compose.foundation.layout.PaddingValues
import com.akari.uicomponents.textFields.config.AkariTextFieldPadding

/**
 * A builder class for creating [AkariTextFieldPadding] instances.
 *
 * This builder allows for the customization of padding values for various internal components
 * of an Akari Text Field. It can be initialized with a base [AkariTextFieldPadding] configuration,
 * allowing for selective overrides of existing padding values.
 *
 * Example usage:
 * ```
 * val customPadding = AkariTextFieldPaddingBuilder().apply {
 *     contentPadding = PaddingValues(16.dp)
 *     leadingIconPadding = PaddingValues(end = 8.dp)
 * }.build()
 * ```
 *
 * @property base The initial padding configuration to start with. Defaults to a standard [AkariTextFieldPadding].
 * @property externalContentPadding Padding applied to the outermost container of the text field.
 * @property contentPadding Padding applied around the primary input content area.
 * @property leadingIconPadding Padding applied specifically to the leading icon.
 * @property trailingIconPadding Padding applied specifically to the trailing icon.
 * @property supportingTextPadding Padding applied to the supporting text area below the input.
 * @property prefixPadding Padding applied to the prefix text.
 * @property suffixPadding Padding applied to the suffix text.
 * @property labelPadding Padding applied to the floating label.
 * @property mainContentPadding Padding applied to the main content wrapper which holds the text input and placeholders.
 */
class AkariTextFieldPaddingBuilder(private val base: AkariTextFieldPadding = AkariTextFieldPadding()) {
    var externalContentPadding: PaddingValues = base.externalContent
    var contentPadding: PaddingValues = base.content
    var leadingIconPadding: PaddingValues = base.leadingIcon
    var trailingIconPadding: PaddingValues = base.trailingIcon
    var supportingTextPadding: PaddingValues = base.supportingText
    var prefixPadding: PaddingValues = base.prefix
    var suffixPadding: PaddingValues = base.suffix
    var labelPadding: PaddingValues = base.label
    var mainContentPadding: PaddingValues = base.mainContent

    fun build() = AkariTextFieldPadding(
        externalContent = externalContentPadding,
        content = contentPadding,
        leadingIcon = leadingIconPadding,
        trailingIcon = trailingIconPadding,
        supportingText = supportingTextPadding,
        prefix = prefixPadding,
        suffix = suffixPadding,
        label = labelPadding,
        mainContent = mainContentPadding
    )
}