package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.akari.uicomponents.textFields.AkariTextField
import com.akari.uicomponents.textFields.AkariTextFieldVariant
import com.akari.uicomponents.textFields.rememberAkariTextFieldState
import com.akari.uicomponents.textFields.state.AkariTextFieldState

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextFieldExample(){
    var value by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val firsFocusRequester = remember { FocusRequester() }
    val shapes = MaterialTheme.shapes
    val state: AkariTextFieldState = rememberAkariTextFieldState(
        value = value,
        onValueChange = { value = it },
        builder = {
            label = {
                Text(text = "Label")
            }
            placeholder = "Placeholder"
            focusRequester = firsFocusRequester
            variant {
                AkariTextFieldVariant.Outlined
            }
            style {
                shape = shapes.large
            }
            behavior {
                autoSelectOnFocus = true
                enabled = true
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus()
                }
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Send
                )
            }
        }
    )
    AkariTextField(state = state)
}