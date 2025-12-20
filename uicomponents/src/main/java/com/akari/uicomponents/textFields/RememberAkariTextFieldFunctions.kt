package com.akari.uicomponents.textFields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.akari.uicomponents.textFields.builders.AkariTextFieldConfigBuilder
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig

@Composable
fun rememberAkariTextFieldConfig(
    builder: AkariTextFieldConfigBuilder.() -> Unit
): AkariTextFieldConfig {
    val currentBuilder = rememberUpdatedState(builder)

    return remember {
        AkariTextFieldConfigBuilder().apply {
            currentBuilder.value(this)
        }.build()
    }
}

@Composable
fun rememberAkariTextFieldConfig(
    vararg keys: Any?,
    builder: AkariTextFieldConfigBuilder.() -> Unit
): AkariTextFieldConfig {
    val currentBuilder = rememberUpdatedState(builder)

    return remember(*keys) {
        AkariTextFieldConfigBuilder().apply {
            currentBuilder.value(this)
        }.build()
    }
}