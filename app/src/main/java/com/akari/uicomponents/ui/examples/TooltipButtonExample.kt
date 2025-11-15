package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.buttons.AkariButtonVariant
import com.akari.uicomponents.buttons.config.AkariButtonConfig
import com.akari.uicomponents.buttons.tooltipButtons.AkariTooltipButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipButtonExample(){
    val shapes = MaterialTheme.shapes
    val colors = MaterialTheme.colorScheme
    AkariTooltipButton(
        variant = AkariButtonVariant.Filled,
        onClick = {},
        buttonConfig = AkariButtonConfig {
            shape = shapes.extraSmall
            border = BorderStroke(width = 3.dp, color = colors.tertiaryContainer)
        },
        tooltipContent = {
            Text(text = "Tooltip Example")
        }
    ){
        Text(text = "Hello World")
    }
}