package com.akari.uicomponents.checkbox

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@Composable
fun AkariCheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.small,
    innerPadding: PaddingValues = PaddingValues(4.dp, 4.dp),
    minSize: DpSize = DpSize(24.dp, 24.dp),
    enabled: Boolean = true,
    colors: AkariCheckBoxColors = AkariCheckBoxDefaults.colors(),
    animationSpec: FiniteAnimationSpec<Float> = tween(durationMillis = 200, easing = FastOutSlowInEasing),
    iconUnselected: (@Composable () -> Unit)? = null,
    iconSelected: @Composable () -> Unit
){
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación de escala cuando se presiona
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMedium),
        label = "scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .clip(shape)
            .sizeIn(minWidth = minSize.width, minHeight = minSize.height)
            .background(
                color = when {
                    !enabled -> colors.disabledBackgroundColor(checked)
                    checked -> colors.checkedBackgroundColor
                    else -> colors.uncheckedBackgroundColor
                },
                shape = shape
            )
            .border(
                width = if (checked) 2.dp else 1.dp, // Borde más grueso cuando está seleccionado
                color = when {
                    !enabled -> colors.disabledBorderColor(checked)
                    checked -> colors.checkedBorderColor
                    else -> colors.uncheckedBorderColor
                },
                shape = shape
            )
            .semantics {
                role = Role.Checkbox
                this.stateDescription = if (checked) "Seleccionado" else "No seleccionado"
            }
            .toggleable(
                value = checked,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = true,
                    color = colors.rippleColor
                ),
                onValueChange = onCheckedChange
            )
            .padding(paddingValues = innerPadding),
        contentAlignment = Alignment.Center
    ) {
        // Animación del icono seleccionado con escala y rotación
        AnimatedVisibility(
            visible = checked,
            enter = fadeIn(animationSpec = animationSpec) +
                    scaleIn(initialScale = 0.8f, animationSpec = animationSpec),
            exit = fadeOut(animationSpec = animationSpec) +
                    scaleOut(targetScale = 0.8f, animationSpec = animationSpec)
        ) {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationZ = if (checked) 0f else -90f
                }
            ) {
                iconSelected()
            }
        }

        // Icono no seleccionado
        iconUnselected?.let { icon ->
            AnimatedVisibility(
                visible = !checked,
                enter = fadeIn(animationSpec = animationSpec) +
                        scaleIn(initialScale = 0.8f, animationSpec = animationSpec),
                exit = fadeOut(animationSpec = animationSpec) +
                        scaleOut(targetScale = 0.8f, animationSpec = animationSpec)
            ) {
                icon()
            }
        }
    }
}