package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import com.akari.uicomponents.textFields.AkariLabelBehavior
import com.akari.uicomponents.textFields.AkariTextField
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig
import com.akari.uicomponents.textFields.rememberAkariTextFieldConfig

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TextFieldExample() {
    val state = TextFieldState()
    var value by remember { mutableStateOf(TextFieldValue("")) }
    val focusManager = LocalFocusManager.current
    val firsFocusRequester = remember { FocusRequester() }
    val shapes = MaterialTheme.shapes
    val typography = MaterialTheme.typography
    val config: AkariTextFieldConfig = rememberAkariTextFieldConfig {
        slots {
            label = {
                Text(
                    text = "Label",
                    style = typography.labelLarge
                )
            }
            placeholder = { Text("Placeholder") }
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                )
            }
        }
        style {
            shape = shapes.extraSmall
            textStyle = typography.titleLarge
        }
        behavior {
            autoSelectOnFocus = true
            labelBehavior = AkariLabelBehavior.FLOATING
            keyboardActions = KeyboardActions {
                focusManager.clearFocus()
            }
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Send
            )
        }
    }

    AkariTextField(
        value = value,
        onValueChange = { value = it },
        config = config,
        focusRequester = firsFocusRequester
    )
}