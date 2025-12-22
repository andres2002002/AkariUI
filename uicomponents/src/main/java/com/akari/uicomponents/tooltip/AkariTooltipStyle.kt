package com.akari.uicomponents.tooltip

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Defines the style configuration for [AkariTooltip].
 *
 * This sealed class allows for customizing the appearance of tooltips, supporting both
 * plain text tooltips and rich content tooltips. It encapsulates common visual properties
 * like shape, dimensions, and elevation, while subclasses provide specific color configurations
 * for different content types.
 *
 * @property shape The shape of the tooltip container. If null, a default shape will be used based on the tooltip type.
 * @property maxWidth The maximum width of the tooltip. If null, a default maximum width will be applied.
 * @property tonalElevation The tonal elevation of the tooltip container, used to distinguish it from the background.
 * @property shadowElevation The shadow elevation of the tooltip container.
 *
 * @see AkariTooltipStyle.PlainTooltip For styling simple, text-based tooltips.
 * @see AkariTooltipStyle.RichTooltip For styling tooltips with titles, rich content, and actions.
 */
@Immutable
sealed class AkariTooltipStyle(
){
    abstract val shape: Shape?
    abstract val maxWidth: Dp?
    abstract val tonalElevation: Dp
    abstract val shadowElevation: Dp

    /**
     * A style definition for a plain tooltip in the Akari UI components system.
     *
     * Plain tooltips usually provide a descriptive text label for an anchor element and appear on long press
     * or hover. They are simpler than [RichTooltip]s and typically do not contain actions or titles.
     *
     * All parameters are optional. If null, the implementation will default to Material 3 standard values.
     *
     * @property containerColor The color of the tooltip's background. If null, uses the default tooltip container color.
     * @property contentColor The color of the content (text/icon) within the tooltip. If null, uses the default tooltip content color.
     * @property shape The shape of the tooltip container. If null, uses the default tooltip shape.
     * @property maxWidth The maximum width of the tooltip. If null, uses the system default max width.
     * @property tonalElevation The tonal elevation of the tooltip, used to distinguish it from the background via color. Defaults to 0.dp.
     * @property shadowElevation The shadow elevation of the tooltip, used to create depth. Defaults to 0.dp.
     */
    @Immutable
    data class PlainTooltip(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        override val shape: Shape? = null,
        override val maxWidth: Dp? = null,
        override val tonalElevation: Dp = 0.dp,
        override val shadowElevation: Dp = 0.dp,
    ): AkariTooltipStyle()

    /**
     * A style configuration for a rich tooltip.
     *
     * Rich tooltips provide more context than plain tooltips and can include a title,
     * a longer description, and an optional action. They are intended for more complex
     * guidance or information about a UI element.
     *
     * @property containerColor The color used for the tooltip's background. If null, the default system color is used.
     * @property contentColor The color used for the main body text (subhead). If null, the default system color is used.
     * @property titleContentColor The color used for the title text. If null, the default system color is used.
     * @property actionContentColor The color used for the action button text/icon. If null, the default system color is used.
     * @property shape The shape of the tooltip container. If null, the default system shape is used.
     * @property maxWidth The maximum width of the tooltip. If null, the default system maximum width is applied.
     * @property tonalElevation The tonal elevation of the tooltip, affecting its color in light/dark themes. Defaults to 0.dp.
     * @property shadowElevation The shadow elevation of the tooltip. Defaults to 0.dp.
     */
    @Immutable
    data class RichTooltip @OptIn(ExperimentalMaterial3Api::class) constructor(
        val containerColor: Color? = null,
        val contentColor: Color? = null,
        val titleContentColor: Color? = null,
        val actionContentColor: Color? = null,
        override val shape: Shape? = null,
        override val maxWidth: Dp? = null,
        override val tonalElevation: Dp = 0.dp,
        override val shadowElevation: Dp = 0.dp,
    ): AkariTooltipStyle()
}