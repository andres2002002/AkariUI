package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.akari.uicomponents.tooltip.AkariTooltip
import com.akari.uicomponents.tooltip.AkariTooltipConfig
import com.akari.uicomponents.tooltip.AkariTooltipStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TooltipButtonExample(){
    val shapes = MaterialTheme.shapes
    val colors = MaterialTheme.colorScheme
    Column() {
        AkariTooltip(
            text = "Tooltip Example",
            tooltipConfig = AkariTooltipConfig(
                persistent = true
            ),
            style = AkariTooltipStyle.PlainTooltip(
                containerColor = colors.primary,
                contentColor = colors.onPrimary
            )
        ) {
            Button(
                onClick = {},
                shape = shapes.extraSmall
            ) {
                Text(text = "Hello World")
            }
        }
        AkariTooltip(
            tooltipContent = {
                Text(text = "Tooltip Example")
            }
        ) {
            Button(
                onClick = {},
                shape = shapes.extraSmall
            ) {
                Text(text = "Hello World")
            }
        }
    }
}