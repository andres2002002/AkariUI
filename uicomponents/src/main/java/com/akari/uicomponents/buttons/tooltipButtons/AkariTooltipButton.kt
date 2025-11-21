package com.akari.uicomponents.buttons.tooltipButtons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.akari.uicomponents.buttons.AkariButtonVariant
import com.akari.uicomponents.buttons.config.AkariButtonConfig

/**
 * IconButton con tooltip configurable.
 *
 * @param variant [AkariButtonVariant] a utilizar en el IconButton.
 * @param onClick Lambda a ejecutar al hacer click en el IconButton.
 * @param modifier [Modifier] a aplicar al IconButton.
 * @param buttonConfig [AkariButtonConfig] a aplicar al IconButton.
 * @param tooltipState [TooltipState] a aplicar al tooltip del IconButton.
 * @param content Composable a utilizar como icono del IconButton.
 * @param tooltipConfig [AkariTooltipConfig] a aplicar al tooltip del IconButton.
 * @param contentDescription Descripción del contenido del IconButton.
 * @param interactionSource [MutableInteractionSource] a aplicar al IconButton.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AkariTooltipButton(
    variant: AkariButtonVariant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    buttonConfig: AkariButtonConfig = AkariButtonConfig(),
    tooltipConfig: AkariTooltipConfig = AkariTooltipConfig(),
    tooltipState: TooltipState = rememberTooltipState(isPersistent = tooltipConfig.isPersistent),
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tooltipContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    TooltipBox(
        modifier = tooltipConfig.modifier,
        state = tooltipState,
        enableUserInput = tooltipConfig.enableUserInput,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(tooltipConfig.position),
        tooltip = {
            tooltipConfig.BuildTooltip(this, tooltipContent)
        }
    ) {
        variant.Render(
            onClick = onClick,
            modifier = modifier.semantics {
                this.contentDescription = contentDescription ?: "Button"
            },
            config = buttonConfig,
            interactionSource = interactionSource,
            content = content
        )
    }
}