package com.akari.uicomponents.textFields.builders

import androidx.compose.ui.focus.FocusProperties
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig

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