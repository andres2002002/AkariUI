package com.akari.uicomponents.buttons

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.akari.uicomponents.buttons.config.AkariButtonConfig

/**
 * Representa una variante de Floating Action Button según Material Design 3.
 *
 * Las variantes disponibles son:
 * - [Filled]: Botón con fondo relleno.
 * - [Outlined]: Botón con borde sin relleno.
 * - [Text]: Botón con texto.
 * - [Icon]: Botón con ícono.
 */
sealed class AkariButtonVariant {

    @Composable
    abstract fun Render(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        config: AkariButtonConfig = AkariButtonConfig(),
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    )

    // -------------------------
    // FILLED
    // -------------------------
    data object Filled : AkariButtonVariant() {

        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariButtonConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            Button(
                onClick = onClick,
                enabled = config.enabled,
                modifier = modifier,
                interactionSource = interactionSource,
                shape = config.shape ?: ButtonDefaults.shape,
                contentPadding = config.contentPadding ?: ButtonDefaults.ContentPadding,
                colors = config.colors ?: ButtonDefaults.buttonColors(),
                elevation = config.elevation ?: ButtonDefaults.buttonElevation(),
                border = config.border
            ) {
                content()
            }
        }
    }

    // -------------------------
    // OUTLINED
    // -------------------------
    data object Outlined : AkariButtonVariant() {

        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariButtonConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            OutlinedButton(
                onClick = onClick,
                enabled = config.enabled,
                modifier = modifier,
                interactionSource = interactionSource,
                shape = config.shape ?: ButtonDefaults.outlinedShape,
                contentPadding = config.contentPadding ?: ButtonDefaults.ContentPadding,
                colors = config.colors ?: ButtonDefaults.outlinedButtonColors(),
                elevation = config.elevation,
                border = config.border ?: ButtonDefaults.outlinedButtonBorder(config.enabled)
            ) {
                content()
            }
        }
    }

    // -------------------------
    // TEXT
    // -------------------------
    data object Text : AkariButtonVariant() {

        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariButtonConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            TextButton(
                onClick = onClick,
                enabled = config.enabled,
                modifier = modifier,
                interactionSource = interactionSource,
                shape = config.shape ?: ButtonDefaults.textShape,
                contentPadding = config.contentPadding ?: ButtonDefaults.TextButtonContentPadding,
                colors = config.colors ?: ButtonDefaults.textButtonColors()
            ) {
                content()
            }
        }
    }

    // -------------------------
    // ICON BUTTON
    // -------------------------
    data object Icon : AkariButtonVariant() {

        @Composable
        override fun Render(
            onClick: () -> Unit,
            modifier: Modifier,
            config: AkariButtonConfig,
            interactionSource: MutableInteractionSource,
            content: @Composable () -> Unit
        ) {
            IconButton(
                onClick = onClick,
                enabled = config.enabled,
                modifier = modifier,
                interactionSource = interactionSource
            ) {
                content()
            }
        }
    }
}