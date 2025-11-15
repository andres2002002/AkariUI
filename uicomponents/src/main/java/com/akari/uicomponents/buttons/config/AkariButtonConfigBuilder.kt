package com.akari.uicomponents.buttons.config

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.ui.graphics.Shape

/**
 * A builder class for creating [AkariButtonConfig] instances.
 *
 * This builder provides a fluent API to customize the appearance and behavior of an Akari button
 * across its different states.
 *
 * Once all desired properties are set, call the [build] method to create an immutable
 * [AkariButtonConfig] object.
 *
 * Example usage:
 * ```
 * val customButtonConfig = AkariButtonConfig {
 *     enabled = false
 *     shape = CircleShape
 *     contentPadding = PaddingValues(16.dp)
 * }
 * ```
 */
class AkariButtonConfigBuilder {
    var enabled: Boolean = true
    var shape: Shape? = null
    var contentPadding: PaddingValues? = null
    var colors: ButtonColors? = null
    var border: BorderStroke? = null
    var elevation: ButtonElevation? = null

    internal fun build(): AkariButtonConfig =
        AkariButtonConfig(
            enabled = enabled,
            shape = shape,
            contentPadding = contentPadding,
            colors = colors,
            border = border,
            elevation = elevation
        )
}

fun AkariButtonConfig(
    builder: AkariButtonConfigBuilder.() -> Unit
): AkariButtonConfig = AkariButtonConfigBuilder().apply(builder).build()
