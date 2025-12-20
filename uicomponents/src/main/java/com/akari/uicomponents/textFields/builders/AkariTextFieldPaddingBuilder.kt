package com.akari.uicomponents.textFields.builders

import androidx.compose.foundation.layout.PaddingValues
import com.akari.uicomponents.textFields.config.AkariTextFieldPadding


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