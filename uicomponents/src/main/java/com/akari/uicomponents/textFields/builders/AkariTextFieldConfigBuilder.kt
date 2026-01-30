package com.akari.uicomponents.textFields.builders

import androidx.compose.ui.focus.FocusProperties
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldDsl

/**
 * A builder class for creating instances of [AkariTextFieldConfig].
 *
 * This class provides a DSL-style API to configure various aspects of an Akari text field,
 * such as its visual style, interaction behavior, composable slots, layout padding,
 * and focus properties.
 *
 * Example usage:
 * ```
 * val config = AkariTextFieldConfigBuilder().apply {
 *     style {
 *         // Configure visual style properties
 *     }
 *     behavior {
 *         // Configure interaction behaviors
 *     }
 *     slots {
 *         // Define leading or trailing icons, etc.
 *     }
 * }.build()
 * ```
 *
 * @see AkariTextFieldConfig
 * @see AkariTextFieldStyleBuilder
 * @see AkariTextFieldBehaviorBuilder
 * @see AkariTextFieldSlotsBuilder
 * @see AkariTextFieldPaddingBuilder
 */
@AkariTextFieldDsl
class AkariTextFieldConfigBuilder {
    private var style: AkariTextFieldStyleBuilder = AkariTextFieldStyleBuilder()
    private var behavior: AkariTextFieldBehaviorBuilder = AkariTextFieldBehaviorBuilder()
    private var slots: AkariTextFieldSlotsBuilder = AkariTextFieldSlotsBuilder()
    private var padding: AkariTextFieldPaddingBuilder = AkariTextFieldPaddingBuilder()
    private var focusProperties: FocusProperties.() -> Unit = {}

    fun style(block: AkariTextFieldStyleBuilder.() -> Unit) {
        style.apply(block)
    }

    fun behavior(block: AkariTextFieldBehaviorBuilder.() -> Unit) {
        behavior.apply(block)
    }

    fun slots(block: AkariTextFieldSlotsBuilder.() -> Unit) {
        slots.apply(block)
    }

    fun padding(block: AkariTextFieldPaddingBuilder.() -> Unit) {
        padding.apply(block)
    }

    fun focusProperties(block: FocusProperties.() -> Unit) {
        focusProperties = block
    }

    internal fun build(): AkariTextFieldConfig = AkariTextFieldConfig(
        style = style.build(),
        behavior = behavior.build(),
        slots = slots.build(),
        focusProperties = focusProperties
    )
}