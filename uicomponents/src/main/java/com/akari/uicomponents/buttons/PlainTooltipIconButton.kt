package com.akari.uicomponents.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlinx.coroutines.launch

/**
 * IconButton con tooltip integrado.
 *
 * @param onClick Callback invocado al hacer clic en el botón
 * @param tooltipText Texto mostrado en el tooltip
 * @param icon Contenido del ícono del botón
 * @param modifier Modificador aplicado al IconButton
 * @param enabled Si el botón está habilitado para interacción
 * @param contentDescription Descripción de accesibilidad. Si es null, usa [tooltipText]
 * @param colors Colores del IconButton en diferentes estados
 * @param tooltipPosition Posición del tooltip relativa al botón
 * @param interactionSource Fuente de interacciones para el botón
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainTooltipIconButton(
    onClick: () -> Unit,
    tooltipText: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    contentDescription: String? = null,
    colors: IconButtonColors = IconButtonDefaults.iconButtonColors(),
    tooltipPosition: TooltipAnchorPosition = TooltipAnchorPosition.Above,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val tooltipState = rememberTooltipState(isPersistent = false)
    val scope = rememberCoroutineScope()

    TooltipBox(
        state = tooltipState,
        enableUserInput = enabled,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(tooltipPosition),
        tooltip = {
            PlainTooltip {
                Text(text = tooltipText)
            }
        }
    ) {
        IconButton(
            onClick = {
                scope.launch { tooltipState.dismiss() }
                onClick()
            },
            modifier = modifier.semantics {
                this.contentDescription = contentDescription ?: tooltipText
            },
            enabled = enabled,
            colors = colors,
            interactionSource = interactionSource
        ) {
            icon()
        }
    }
}