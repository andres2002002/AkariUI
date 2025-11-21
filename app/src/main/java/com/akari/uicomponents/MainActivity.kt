package com.akari.uicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.textFields.AkariTextField
import com.akari.uicomponents.textFields.AkariTextFieldState
import com.akari.uicomponents.textFields.AkariTextFieldVariant
import com.akari.uicomponents.textFields.state.AkariTextFieldState
import com.akari.uicomponents.textFields.rememberAkariOutlinedTextFieldState
import com.akari.uicomponents.ui.examples.TooltipButtonExample
import com.akari.uicomponents.ui.theme.AkariUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AkariUITheme {
                Scaffold() { padding ->
                    Box(modifier = Modifier.padding(padding)) {
                        TooltipButtonExample()
                    }
                }
            }
        }
    }
}