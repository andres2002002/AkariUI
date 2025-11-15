package com.akari.uicomponents.buttons.config

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Shape

/**
 * Configuration data class for styling and behavior of an Akari button.
 *
 * This class encapsulates various properties that define the appearance and interaction
 * characteristics of a button, such as its type, size, icon, and text. It allows for
 * a consistent and reusable way to configure button components across the application.
 *
 * @property enabled A boolean flag indicating whether the button is enabled and interactive.
 * @property shape The optional [Shape] to be applied to the button's surface.
 * @property contentPadding The optional [PaddingValues] to be applied to the button's content.
 * @property colors The optional [ButtonColors] to be applied to the button's appearance.
 * @property elevation The optional [ButtonElevation] to be applied to the button's surface.
 * @property border The optional [BorderStroke] to be applied to the button's surface.
 */
data class AkariButtonConfig(
    val enabled: Boolean = true,
    val shape: Shape? = null,
    val contentPadding: PaddingValues? = null,
    val colors: ButtonColors? = null,
    val elevation: ButtonElevation? = null,
    val border: BorderStroke? = null
)