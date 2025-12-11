package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Immutable
object AkariTextFieldDefaults {

    /** Default shape for an [OutlinedTextField]. */
    val shape: Shape
        @Composable get() = OutlinedTextFieldDefaults.shape

    /**
     * The default min height applied to an [OutlinedTextField]. Note that you can override it by
     * applying Modifier.heightIn directly on a text field.
     */
    val MinHeight = 56.dp

    /**
     * The default min width applied to an [OutlinedTextField]. Note that you can override it by
     * applying Modifier.widthIn directly on a text field.
     */
    val MinWidth = 280.dp

    /** The default thickness of the border in [OutlinedTextField] in unfocused state. */
    val UnfocusedBorderThickness = 1.dp

    /** The default thickness of the border in [OutlinedTextField] in focused state. */
    val FocusedBorderThickness = 2.dp
}