package com.akari.uicomponents.tooltip

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipScope
import androidx.compose.material3.TooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * A composable that shows a tooltip when the user long-presses or hovers over the [content].
 *
 * This component is a wrapper around Material 3's `TooltipBox` and provides a simplified API
 * for creating and customizing tooltips. It supports both plain and rich tooltip styles.
 *
 * There are two overloads for this function:
 * 1.  A simple version that takes a `text` string for the tooltip.
 * 2.  A more flexible version that accepts a `tooltipContent` composable lambda, allowing for
 *     custom content within the tooltip.
 *
 * Example of a simple text tooltip:
 * ```
 * AkariTooltip(text = "Add to favorites") {
 *     Icon(
 *         imageVector = Icons.Default.Favorite,
 *         contentDescription = "Favorite"
 *     )
 * }
 * ```
 *
 * Example of a rich tooltip with custom content:
 * ```
 * AkariTooltip(
 *     style = AkariTooltipStyle.RichTooltip(),
 *     tooltipContent = {
 *         Column(modifier = Modifier.padding(8.dp)) {
 *             Text("Information", style = MaterialTheme.typography.titleSmall)
 *             Text("This is a detailed description inside a rich tooltip.")
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AkariTooltip(
    text: String,
    style: AkariTooltipStyle = AkariTooltipStyle.PlainTooltip(),
    tooltipConfig: AkariTooltipConfig = AkariTooltipConfig(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit
) {
    AkariTooltip(
        style = style,
        tooltipConfig = tooltipConfig,
        modifier = modifier,
        enabled = enabled,
        tooltipContent = { Text(text = text) },
        content = content
    )
}

/**
 * A versatile tooltip component wrapper that supports both simple text and complex composable content.
 *
 * This component utilizes Material3's [TooltipBox] to manage tooltip state, positioning, and interactions (hover/long-press).
 * It offers two variants: one for simple text strings and another for custom composable content within the tooltip.
 *
 * @param style Defines the visual appearance of the tooltip (e.g., [AkariTooltipStyle.PlainTooltip] or [AkariTooltipStyle.RichTooltip]).
 *              Defaults to a plain tooltip style.
 * @param tooltipConfig Configuration object for behavior, such as persistence, position (Top, Bottom, Start, End),
 *                      and user input handling. Defaults to standard [AkariTooltipConfig].
 * @param modifier Modifier to be applied to the `TooltipBox` container.
 * @param enabled Whether the tooltip functionality is active. If `false`, only the [content] is rendered without the tooltip logic.
 * @param tooltipContent A composable lambda defining the content to be shown *inside* the tooltip bubble.
 *                       (Only available in the composable-content overload).
 * @param content The anchor component (e.g., Button, Icon) that triggers the tooltip when interacted with.
 *
 * @see AkariTooltipStyle
 * @see AkariTooltipConfig
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AkariTooltip(
    style: AkariTooltipStyle = AkariTooltipStyle.PlainTooltip(),
    tooltipConfig: AkariTooltipConfig = AkariTooltipConfig(),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    tooltipContent: @Composable () -> Unit,
    content: @Composable () -> Unit
) {
    if (!enabled) {
        Box(modifier = modifier) { content() }
        return
    }

    // Utilizamos el estado de tooltip de Material3 para control fino.
    val tooltipState = remember(tooltipConfig.persistent) {
        TooltipState(isPersistent = tooltipConfig.persistent)
    }

    // Mapear la posición simple a un PositionProvider de Material3.
    val anchorPosition = remember(tooltipConfig.position) {
        when (tooltipConfig.position) {
            TooltipPosition.Top -> TooltipAnchorPosition.Above
            TooltipPosition.Bottom -> TooltipAnchorPosition.Below
            TooltipPosition.Start -> TooltipAnchorPosition.Start
            TooltipPosition.End -> TooltipAnchorPosition.End
        }
    }

    // Usamos TooltipBox que gestiona enfoque, hover y la colocación
    TooltipBox(
        tooltip = { BuildToolTip(style, tooltipContent) },
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(anchorPosition),
        enableUserInput = tooltipConfig.enableUserInput,
        state = tooltipState,
        modifier = modifier
    ) {
        // slot: lo que envuelves (botón, icon, row...)
        content()
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TooltipScope.BuildToolTip(
    style: AkariTooltipStyle,
    content: @Composable () -> Unit
){
    when (style) {
        is AkariTooltipStyle.PlainTooltip -> {
            PlainTooltip(
                containerColor = style.containerColor?: TooltipDefaults.plainTooltipContainerColor,
                contentColor = style.contentColor?: TooltipDefaults.plainTooltipContentColor,
                shape = style.shape ?: TooltipDefaults.plainTooltipContainerShape,
                tonalElevation = style.tonalElevation,
                shadowElevation = style.shadowElevation,
                maxWidth = style.maxWidth ?: TooltipDefaults.plainTooltipMaxWidth
            ) {
                content()
            }
        }

        is AkariTooltipStyle.RichTooltip -> {
            val colors = TooltipDefaults.richTooltipColors()
            RichTooltip(
                shape = style.shape ?: TooltipDefaults.richTooltipContainerShape,
                colors = colors.copy(
                    containerColor = style.containerColor ?: colors.containerColor,
                    contentColor = style.contentColor ?: colors.contentColor,
                    actionContentColor = style.actionContentColor ?: colors.actionContentColor,
                    titleContentColor = style.titleContentColor ?: colors.titleContentColor
                ),
                tonalElevation = style.tonalElevation,
                shadowElevation = style.shadowElevation,
                maxWidth = style.maxWidth ?: TooltipDefaults.richTooltipMaxWidth,
            ) {
                content()
            }
        }
    }
}