package com.akari.uicomponents.tooltip

import androidx.compose.runtime.Immutable

/**
 * Configuration data class for customizing the behavior and appearance of an [AkariTooltip].
 *
 * This class is marked as [Immutable] to indicate that its instances will not change after creation,
 * allowing Compose to optimize recompositions.
 *
 * @property enableUserInput Determines if the tooltip area accepts user input (e.g., clicks).
 * Defaults to `true`. If `false`, events will pass through the tooltip to the underlying content.
 * @property persistent If `true`, the tooltip remains visible until explicitly dismissed or the
 * user interacts outside of it. If `false`, the tooltip follows standard transient behavior
 * (e.g., disappearing on touch up or after a delay). Defaults to `false`.
 * @property position Specifies the preferred location of the tooltip relative to its anchor.
 * Defaults to [TooltipPosition.Top].
 */
@Immutable
data class AkariTooltipConfig(
    val enableUserInput: Boolean = true,
    val persistent: Boolean = false,
    val position: TooltipPosition = TooltipPosition.Top
)