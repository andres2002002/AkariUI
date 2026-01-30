package com.akari.uicomponents.checkbox

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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

/**
 * A highly customizable CheckBox component that supports custom animations, shapes, and icons for both checked and unchecked states.
 *
 * This component provides a polished, interactive experience with built-in press animations (scale effect) and transition animations
 * (fade and scale with rotation) when the state changes. It adheres to Material Design accessibility standards by reporting semantic
 * state descriptions.
 *
 * @param checked Whether this checkbox is currently selected.
 * @param onCheckedChange Called when the user clicks the checkbox and requests a change in the state.
 * @param modifier The modifier to be applied to the CheckBox layout.
 * @param shape The shape of the checkbox background and border. Defaults to [MaterialTheme.shapes.small].
 * @param innerPadding Padding applied inside the checkbox container, surrounding the icon. Defaults to 4.dp on all sides.
 * @param minSize The minimum width and height of the touch target and visual container. Defaults to 24.dp x 24.dp.
 * @param enabled Controls the enabled state of this checkbox. When `false`, this component will not respond to user input,
 * and it will appear visually disabled and distinct from the enabled state.
 * @param colors [AkariCheckBoxColors] that will be used to resolve the colors used for this checkbox in different states.
 * See [AkariCheckBoxDefaults.colors].
 * @param animationSpec The animation specification used for the icon appearance/disappearance transitions (fade and scale).
 * Defaults to a tween animation with FastOutSlowInEasing.
 * @param iconUnselected An optional composable to display when the checkbox is in the unchecked state. If null, nothing is shown
 * when unchecked.
 * @param iconSelected A composable to display when the checkbox is in the checked state.
 */
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
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "PressScale"
    )

    val transition = updateTransition(targetState = checked, label = "CheckedTransition")

    val backgroundColor by transition.animateColor(label = "BgColor") { target ->
        colors.boxColor(enabled, target)
    }

    val borderColor by transition.animateColor(label = "BorderColor") { target ->
        colors.borderColor(enabled, target)
    }

    val markColor by transition.animateColor(label = "MarkColor") { target ->
        if (target) colors.checkedCheckmarkColor else colors.uncheckedCheckmarkColor
    }

    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(shape)
            .sizeIn(minWidth = minSize.width, minHeight = minSize.height)
            .background(
                color = backgroundColor,
                shape = shape
            )
            .border(
                width = if (checked) 2.dp else 1.dp, // Borde más grueso cuando está seleccionado
                color = borderColor,
                shape = shape
            )
            .toggleable(
                value = checked,
                enabled = enabled,
                interactionSource = interactionSource,
                indication = ripple(
                    bounded = true,
                    color = colors.rippleColor
                ),
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(paddingValues = innerPadding),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalContentColor provides markColor) {
            AnimatedContent(
                targetState = checked,
                transitionSpec = {
                    (fadeIn(animationSpec) + scaleIn(initialScale = 0.8f))
                        .togetherWith(fadeOut(animationSpec) + scaleOut(targetScale = 0.8f))
                },
                label = "IconContent"
            ) { isChecked ->
                if (isChecked) {
                    iconSelected()
                } else {
                    iconUnselected?.invoke() ?: Box(Modifier.size(minSize))
                }
            }
        }
    }
}