package com.akari.uicomponents.textFields

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
        override val style = AkariTextFieldStyle(
            contentPadding = PaddingValues(16.dp),
            borderThickness = 1.dp,
            shape = RoundedCornerShape(4.dp)
        )
        override val behavior = AkariTextFieldBehavior()
    }

    data object Filled : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            borderThickness = 0.dp,
            shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
        )
        override val behavior = AkariTextFieldBehavior()
    }

    data object Search : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            borderThickness = 1.dp,
            textStyle = TextStyle(fontSize = 14.sp),
            shape = RoundedCornerShape(24.dp)
        )
        override val behavior = AkariTextFieldBehavior(
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            )
        )
    }

    data object Password : AkariTextFieldVariant() {
        override val style = AkariTextFieldStyle(
            contentPadding = PaddingValues(16.dp),
            shape = RoundedCornerShape(4.dp)
        )
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
        override val style = AkariTextFieldStyle(
            contentPadding = PaddingValues(16.dp),
            minHeightInnerTextField = 80.dp,
            shape = RoundedCornerShape(8.dp)
        )
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