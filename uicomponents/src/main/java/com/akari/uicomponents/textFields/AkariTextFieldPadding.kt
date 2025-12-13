package com.akari.uicomponents.textFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp

/**
 * Data class that holds the padding values for the different components of an [AkariTextField].
 *
 * This class allows for detailed customization of the spacing around the text field's content, icons,
 * supporting text, prefix, suffix, and label.
 *
 * @param externalContentPadding The padding for the entire text field component, including the label
 *   and supporting text.
 * @param contentPadding The padding for the main content area of the text field, which includes the
 *   input text, placeholder, and icons.
 * @param leadingIconPadding The padding specifically for the leading icon.
 * @param trailingIconPadding The padding specifically for the trailing icon.
 * @param supportingTextPadding The padding for the supporting text displayed below the text field.
 * @param prefixPadding The padding specifically for the prefix content.
 * @param suffixPadding The padding specifically for the suffix content.
 * @param labelPadding The padding for the label, whether it's external or floating.
 * @param mainContentPadding The padding applied to the main content container, which wraps the
 *   input text, placeholder, prefix, and suffix.
 */
class AkariTextFieldPadding (
    var externalContentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
    var contentPadding: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    var leadingIconPadding: PaddingValues = PaddingValues(end = 4.dp),
    var trailingIconPadding: PaddingValues = PaddingValues(start = 4.dp),
    var supportingTextPadding: PaddingValues = PaddingValues(start = 16.dp, end = 16.dp),
    var prefixPadding: PaddingValues = PaddingValues(end = 4.dp),
    var suffixPadding: PaddingValues = PaddingValues(start = 4.dp),
    var labelPadding: PaddingValues = PaddingValues(vertical = 4.dp, horizontal = 4.dp),
    var mainContentPadding: PaddingValues = PaddingValues(all = 0.dp)
)