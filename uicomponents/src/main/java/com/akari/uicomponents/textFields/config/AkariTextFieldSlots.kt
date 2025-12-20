package com.akari.uicomponents.textFields.config

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * A configuration holder for slot-based composables within the Akari Text Field components.
 *
 * This class groups optional composable slots that adorn or provide context to the text field,
 * such as labels, icons, placeholders, and supporting text. It is marked as [Stable] to
 * allow the Compose compiler to optimize recompositions when these slot definitions haven't changed.
 *
 * @property label An optional composable label that is displayed inside the text field container.
 * @property placeholder An optional composable placeholder displayed when the text field is empty and focused.
 * @property prefix An optional composable prefix displayed before the input text.
 * @property suffix An optional composable suffix displayed after the input text.
 * @property supportingText An optional composable displayed below the text field, often used for helper text or error messages.
 * @property leadingIcon An optional composable displayed at the start of the text field container. It receives a boolean indicating if the field is in an error state.
 * @property trailingIcon An optional composable displayed at the end of the text field container. It receives a boolean indicating if the field is in an error state.
 */
@Stable
class AkariTextFieldSlots(
    val label: (@Composable () -> Unit)? = null,
    val placeholder: (@Composable () -> Unit)? = null,
    val prefix: (@Composable () -> Unit)? = null,
    val suffix: (@Composable () -> Unit)? = null,
    val supportingText: (@Composable () -> Unit)? = null,
    val leadingIcon: (@Composable (Boolean) -> Unit)? = null,
    val trailingIcon: (@Composable (Boolean) -> Unit)? = null
)
