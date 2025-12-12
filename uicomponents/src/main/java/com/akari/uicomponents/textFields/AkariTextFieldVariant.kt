package com.akari.uicomponents.textFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.akari.uicomponents.textFields.AkariTextFieldBehavior
import com.akari.uicomponents.textFields.AkariTextFieldStyle

sealed class AkariTextFieldVariant {
    abstract val style: AkariTextFieldStyle
    abstract val behavior: AkariTextFieldBehavior

    data object Outlined : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle()
        override val behavior = AkariTextFieldBehavior()
    }

    data object Filled : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle()
        override val behavior = AkariTextFieldBehavior()
    }

    data object Search : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle(
            textStyle = TextStyle(fontSize = 14.sp)
        )
        override val behavior = AkariTextFieldBehavior(
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }

    data object Password : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle()
        override val behavior = AkariTextFieldBehavior(
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            )
        )
    }

    data object MultilineMessage : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle()
        override val behavior = AkariTextFieldBehavior(
            singleLine = false,
            minLines = 3,
            maxLines = 10,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences
            )
        )
    }

    data class Custom(
        override val style: AkariTextFieldStyle,
        override val behavior: AkariTextFieldBehavior = AkariTextFieldBehavior()
    ) : AkariTextFieldVariant()
}