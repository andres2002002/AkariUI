package com.akari.uicomponents.textFields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.akari.uicomponents.textFields.builders.AkariTextFieldConfigBuilder
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig
import com.akari.uicomponents.textFields.config.AkariTextFieldStyle
import com.akari.uicomponents.textFields.config.AkariTextFieldPadding
import com.akari.uicomponents.textFields.config.AkariTextFieldSlots
import com.akari.uicomponents.textFields.config.AkariTextFieldBehavior

/**
 * Creates and remembers an [AkariTextFieldConfig] using the provided builder lambda.
 *
 * This function ensures that the configuration object is created only once
 * and survives recompositions. It is a wrapper around [remember] that simplifies the
 * creation of [AkariTextFieldConfig] instances using the [AkariTextFieldConfigBuilder] DSL.
 *
 * The builder lambda is wrapped in [rememberUpdatedState] to ensure the most recent
 * definition is used during the initial composition or when re-calculation occurs,
 * though typically configuration is static.
 *
 * Exposes configuration options for [AkariTextFieldSlots], [AkariTextFieldPadding],
 * [AkariTextFieldStyle], and [AkariTextFieldBehavior].
 *
 * @param builder A lambda receiver on [AkariTextFieldConfigBuilder] to define the text field's properties.
 * @return A remembered instance of [AkariTextFieldConfig].
 */
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

/**
 * Creates and remembers an [AkariTextFieldConfig] using the provided builder lambda.
 *
 * This function ensures that the configuration object is created only once (or recreated if keys change)
 * and survives recompositions. It is a wrapper around [remember] that simplifies the
 * creation of [AkariTextFieldConfig] instances using the [AkariTextFieldConfigBuilder] DSL.
 *
 * The builder lambda is wrapped in [rememberUpdatedState] to ensure the most recent
 * definition is used during the initial composition or when re-calculation occurs,
 * though typically configuration is static.
 *
 * @param keys The set of keys to be used by [remember]. The configuration will be re-calculated
 * if any of these keys change.
 * @param builder A lambda receiver on [AkariTextFieldConfigBuilder] to define the text field's properties.
 * @return A remembered instance of [AkariTextFieldConfig].
 */
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