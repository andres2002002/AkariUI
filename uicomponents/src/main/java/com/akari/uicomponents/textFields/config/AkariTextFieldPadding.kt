package com.akari.uicomponents.textFields.config

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.textFields.AkariTextField

/**
 * Data class that holds the padding values for the different components of an [AkariTextField].
 *
 * This class allows for detailed customization of the spacing around the text field's content, icons,
 * supporting text, prefix, suffix, and label.
 *
 * @param externalContent The padding for the entire text field component, including the label
 *   and supporting text.
 * @param content The padding for the main content area of the text field, which includes the
 *   input text, placeholder, and icons.
 * @param leadingIcon The padding specifically for the leading icon.
 * @param trailingIcon The padding specifically for the trailing icon.
 * @param supportingText The padding for the supporting text displayed below the text field.
 * @param prefix The padding specifically for the prefix content.
 * @param suffix The padding specifically for the suffix content.
 * @param label The padding for the label, whether it's external or floating.
 * @param mainContent The padding applied to the main content container, which wraps the
 *   input text, placeholder, prefix, and suffix.
 */
@Immutable
data class AkariTextFieldPadding(
    val externalContent: PaddingValues = PaddingValues(8.dp),
    val content: PaddingValues = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
    val leadingIcon: PaddingValues = PaddingValues(end = 4.dp),
    val trailingIcon: PaddingValues = PaddingValues(start = 4.dp),
    val supportingText: PaddingValues = PaddingValues(horizontal = 16.dp),
    val prefix: PaddingValues = PaddingValues(end = 4.dp),
    val suffix: PaddingValues = PaddingValues(start = 4.dp),
    val label: PaddingValues = PaddingValues(4.dp),
    val mainContent: PaddingValues = PaddingValues(0.dp)
)