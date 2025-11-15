package com.akari.uicomponents.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.akari.uicomponents.buttons.config.AkariFabConfig

/**
 * Representa una variante de Floating Action Button según Material Design 3.
 *
 * Las variantes disponibles son:
 * - [Small]: FAB pequeño (40dp)
 * - [Normal]: FAB estándar (56dp)
 * - [Large]: FAB grande (96dp)
 * - Extended (Soon): FAB expandido con texto e ícono opcional (Unavaiable)
 */
sealed class AkariFabVariant {

    @Composable
    protected fun resolveColors(config: AkariFabConfig): Pair<Color, Color> {
        val container = config.containerColor ?: FloatingActionButtonDefaults.containerColor
        val content = config.contentColor ?: contentColorFor(container)
        return container to content
    }

    /**
     * Renders the Floating Action Button variant.
     *
     * This function is implemented by each variant to draw the appropriate Material Design FAB.
     *
     * @param onClick The lambda to be executed when the button is clicked.
     * @param modifier The [Modifier] to be applied to the button.
     * @param config The [AkariFabConfig] for customizing the button's appearance, such as shape,
     * color, and elevation.
     * @param interactionSource The [MutableInteractionSource] representing the stream of
     * Interactions for this button. You can create and pass in your own remembered
     * `MutableInteractionSource` to observe `Interaction`s and customize the appearance / behavior of
     * this button in different states.
     * @param content The composable content to be displayed inside the button.
     */
    @Composable
    abstract fun Render(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        config: AkariFabConfig = AkariFabConfig(),
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    )

    data object Small : AkariFabVariant() {
        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariFabConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            val (container, contentColor) = resolveColors(config)

            SmallFloatingActionButton(
                onClick = onClick,
                modifier = modifier,
                shape = config.shape ?: FloatingActionButtonDefaults.smallShape,
                containerColor = container,
                contentColor = contentColor,
                elevation = config.elevation ?: FloatingActionButtonDefaults.elevation(),
                interactionSource = interactionSource
            ) { content() }
        }
    }

    data object Normal : AkariFabVariant() {
        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariFabConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            val (container, contentColor) = resolveColors(config)
            FloatingActionButton(
                modifier = modifier,
                onClick = onClick,
                shape = config.shape?: FloatingActionButtonDefaults.shape,
                containerColor = container,
                contentColor = contentColor,
                elevation = config.elevation?: FloatingActionButtonDefaults.elevation(),
                interactionSource = interactionSource
            ) { content() }
        }
    }

    data object Large : AkariFabVariant() {
        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariFabConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            val (container, contentColor) = resolveColors(config)
            LargeFloatingActionButton(
                modifier = modifier,
                onClick = onClick,
                shape = config.shape?: FloatingActionButtonDefaults.largeShape,
                containerColor = container,
                contentColor = contentColor,
                elevation = config.elevation?: FloatingActionButtonDefaults.elevation(),
                interactionSource = interactionSource
            ) { content() }
        }
    }
}