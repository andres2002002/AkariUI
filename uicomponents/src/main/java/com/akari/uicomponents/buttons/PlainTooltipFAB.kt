package com.akari.uicomponents.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import kotlinx.coroutines.launch

/**
 * FloatingActionButton con tooltip integrado.
 *
 * @param onClick Callback invocado al hacer clic en el FAB
 * @param tooltipText Texto mostrado en el tooltip
 * @param icon Contenido del ícono del FAB
 * @param modifier Modificador aplicado al FAB
 * @param contentDescription Descripción de accesibilidad. Si es null, usa [tooltipText]
 * @param enableUserInput Si el FAB está habilitado para interacción
 * @param containerColor Color del contenedor del FAB
 * @param contentColor Color del contenido (ícono) del FAB
 * @param shape Forma del FAB
 * @param elevation Elevación del FAB en diferentes estados
 * @param tooltipPosition Posición del tooltip relativa al FAB
 * @param interactionSource Fuente de interacciones para el FAB
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlainTooltipFAB(
    onClick: () -> Unit,
    tooltipText: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    enableUserInput: Boolean = true,
    containerColor: Color = FloatingActionButtonDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    shape: Shape = FloatingActionButtonDefaults.shape,
    elevation: FloatingActionButtonElevation = FloatingActionButtonDefaults.elevation(),
    tooltipPosition: TooltipAnchorPosition = TooltipAnchorPosition.Above,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val tooltipState = rememberTooltipState(isPersistent = false)
    val scope = rememberCoroutineScope()

    TooltipBox(
        state = tooltipState,
        enableUserInput = enableUserInput,
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(tooltipPosition),
        tooltip = {
            PlainTooltip {
                Text(text = tooltipText)
            }
        }
    ) {
        FloatingActionButton(
            onClick = {
                scope.launch { tooltipState.dismiss() }
                onClick()
            },
            modifier = modifier.semantics {
                this.contentDescription = contentDescription ?: tooltipText
            },
            containerColor = containerColor,
            contentColor = contentColor,
            shape = shape,
            elevation = elevation,
            interactionSource = interactionSource
        ) {
            icon()
        }
    }
}