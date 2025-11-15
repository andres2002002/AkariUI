package com.akari.uicomponents.buttons.tooltipButtons

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

class AkariTooltipConfigBuilder {
    var title: @Composable (() -> Unit)? = null
    var action: @Composable (() -> Unit)? = null
    var modifier: Modifier = Modifier
    @OptIn(ExperimentalMaterial3Api::class)
    var position: TooltipAnchorPosition = TooltipAnchorPosition.Above
    var isPersistent: Boolean = false
    var enableUserInput: Boolean = true
    var containerColor: Color? = null
    var contentColor: Color? = null
    var shape: Shape? = null
    var tonalElevation: Dp? = null
    var shadowElevation: Dp? = null

    @OptIn(ExperimentalMaterial3Api::class)
    internal fun build(): AkariTooltipConfig =
        AkariTooltipConfig(
            title = title,
            action = action,
            modifier = modifier,
            position = position,
            isPersistent = isPersistent,
            enableUserInput = enableUserInput,
            containerColor = containerColor,
            contentColor = contentColor,
            shape = shape,
            tonalElevation = tonalElevation,
            shadowElevation = shadowElevation
        )
}

fun AkariTooltipConfig(
    builder: AkariTooltipConfigBuilder.() -> Unit
): AkariTooltipConfig = AkariTooltipConfigBuilder().apply(builder).build()