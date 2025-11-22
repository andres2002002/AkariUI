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
import com.akari.uicomponents.buttons.AkariFabVariant
import com.akari.uicomponents.buttons.config.AkariFabConfig

/**
 * FAB con tooltip configurable.
 *
 * @param variant [AkariFabVariant] a utilizar en el FAB.
 * @param onClick Callback invocado al hacer clic en el FAB.
 * @param modifier [Modifier] para personalizar el FAB.
 * @param fabConfig [AkariFabConfig] para personalizar el FAB.
 * @param icon Contenido del FAB.
 * @param tooltipConfig [AkariTooltipConfig] para personalizar el tooltip.
 * @param contentDescription Descripción del contenido del FAB.
 * @param interactionSource [MutableInteractionSource] para personalizar la interacción del FAB.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AkariTooltipFab(
    variant: AkariFabVariant,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fabConfig: AkariFabConfig = AkariFabConfig(),
    tooltipConfig: AkariTooltipConfig = AkariTooltipConfig(),
    tooltipState: TooltipState = rememberTooltipState(isPersistent = tooltipConfig.isPersistent),
    contentDescription: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tooltipContent: @Composable () -> Unit = {},
    icon: @Composable () -> Unit,
) {
    TooltipBox(
        modifier = tooltipConfig.modifier,
        state = tooltipState,
        enableUserInput = tooltipConfig.enableUserInput,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(tooltipConfig.position),
        tooltip = { tooltipConfig.BuildTooltip(this, tooltipContent) }
    ) {
        variant.Render(
            onClick = onClick,
            modifier = modifier.semantics {
                this.contentDescription = contentDescription ?: "FAB"
            },
            config = fabConfig,
            interactionSource = interactionSource,
            content = icon
        )
    }
}