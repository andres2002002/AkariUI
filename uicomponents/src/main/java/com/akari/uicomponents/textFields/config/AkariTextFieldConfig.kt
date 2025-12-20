package com.akari.uicomponents.textFields.config

import androidx.compose.runtime.Immutable
import androidx.compose.ui.focus.FocusProperties

/**
 * Configuration data class for customizing the appearance and behavior of an Akari Text Field.
 *
 * This class serves as a central entry point for styling and configuring the text field, aggregating
 * distinct configuration categories into a single immutable object. It uses default values for all
 * parameters, allowing for granular customization where only specific aspects need to be modified.
 *
 * @property style Defines the visual styling attributes such as colors, typography, and borders.
 * @property padding Defines the internal layout spacing and content padding.
 * @property behavior Controls functional aspects like keyboard options, visual transformation, and input handling.
 * @property slots Contains composable slots for optional UI elements (e.g., leading/trailing icons, prefix/suffix).
 * @property focusProperties A lambda scope for applying custom Compose [FocusProperties] to the text field.
 */
@Immutable
data class AkariTextFieldConfig(
    val style: AkariTextFieldStyle = AkariTextFieldStyle(),
    val padding: AkariTextFieldPadding = AkariTextFieldPadding(),
    val behavior: AkariTextFieldBehavior = AkariTextFieldBehavior(),
    val slots: AkariTextFieldSlots = AkariTextFieldSlots(),
    val focusProperties: FocusProperties.() -> Unit = {}
)