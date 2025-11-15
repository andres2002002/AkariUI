package com.akari.uicomponents.buttons.tooltipButtons

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TooltipScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


/**
 * Configuración completa para tooltips personalizables.
 *
 * @param title Título del tooltip
 * @param action Acción del tooltip
 * @param isRichTooltip Si el tooltip es rico
 * @param position Posición del tooltip relativa al componente
 * @param isPersistent Si el tooltip permanece visible hasta que el usuario lo cierre
 * @param enableUserInput Si el usuario puede interactuar con el tooltip
 * @param containerColor Color del contenedor del tooltip
 * @param contentColor Color del texto del tooltip
 * @param shape Forma del contenedor del tooltip
 * @param tonalElevation Elevación tonal del tooltip
 * @param shadowElevation Elevación de sombra del tooltip
 * @param modifier Modificador aplicado al contenedor del tooltip
 */
data class AkariTooltipConfig @OptIn(ExperimentalMaterial3Api::class) constructor(
    val title: @Composable (() -> Unit)? = null,
    val action: @Composable (() -> Unit)? = null,
    val isRichTooltip: Boolean = false,
    val position: TooltipAnchorPosition = TooltipAnchorPosition.Above,
    val isPersistent: Boolean = false,
    val enableUserInput: Boolean = true,
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val shape: Shape? = null,
    val tonalElevation: Dp? = null,
    val shadowElevation: Dp? = null,
    val modifier: Modifier = Modifier
){
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    internal fun BuildTooltip(
        scope: TooltipScope,
        content: @Composable () -> Unit,
    ){
        if (isRichTooltip || title != null || action != null) {
            // Tooltip rico personalizado
            scope.RichTooltip(
                modifier = modifier,
                colors = TooltipDefaults.richTooltipColors(
                    containerColor = containerColor ?: TooltipDefaults.plainTooltipContainerColor,
                    contentColor = contentColor ?: TooltipDefaults.plainTooltipContentColor
                ),
                shape = shape ?: TooltipDefaults.richTooltipContainerShape,
                tonalElevation = tonalElevation ?: 0.dp,
                shadowElevation = shadowElevation ?: 0.dp,
                title = title,
                action = action,
                text = content
            )
        } else {
            // Tooltip simple
            scope.PlainTooltip(
                modifier = modifier,
                shape = shape ?: TooltipDefaults.plainTooltipContainerShape,
                containerColor = containerColor ?: TooltipDefaults.plainTooltipContainerColor,
                contentColor = contentColor ?: TooltipDefaults.plainTooltipContentColor,
                tonalElevation = tonalElevation ?: 0.dp,
                shadowElevation = shadowElevation ?: 0.dp,
                content = content
            )
        }
    }
}