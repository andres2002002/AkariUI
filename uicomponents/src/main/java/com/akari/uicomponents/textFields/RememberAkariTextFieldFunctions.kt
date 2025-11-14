package com.akari.uicomponents.textFields

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import com.akari.uicomponents.textFields.state.AkariTextFieldState
import com.akari.uicomponents.textFields.state.AkariTextFieldStateBuilder

/**
 * Crea un AkariTextFieldState usando un builder con DSL.
 *
 * @param value El valor actual del TextField
 * @param onValueChange Callback cuando el valor cambia
 * @param builder Configuración del estado usando DSL
 * @return Estado configurado del TextField
 */
fun AkariTextFieldState(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    return AkariTextFieldStateBuilder(value, onValueChange).apply(builder).build()
}

/**
 * Crea y recuerda un AkariTextFieldState que persiste entre recomposiciones.
 *
 * IMPORTANTE: Esta función retorna directamente el estado, no un State<AkariTextFieldState>.
 * Usar State<> aquí causa recomposiciones innecesarias.
 *
 * @param value El valor actual del TextField
 * @param onValueChange Callback cuando el valor cambia
 * @param builder Configuración del estado usando DSL
 * @return Estado recordado del TextField
 */
@Composable
fun rememberAkariTextFieldState(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    // Solo recrear el estado cuando cambien los keys importantes
    return remember(value, onValueChange) {
        AkariTextFieldState(value, onValueChange, builder)
    }
}

/**
 * Sobrecarga para trabajar con String en lugar de TextFieldValue.
 * Simplifica el uso común donde no necesitas control fino del cursor.
 *
 * @param text El texto actual
 * @param onTextChange Callback cuando el texto cambia
 * @param builder Configuración del estado usando DSL
 */
@Composable
fun rememberAkariTextFieldState(
    text: String,
    onTextChange: (String) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    val textFieldValue = remember(text) {
        TextFieldValue(text)
    }

    return rememberAkariTextFieldState(
        value = textFieldValue,
        onValueChange = { onTextChange(it.text) },
        builder = builder
    )
}

@Composable
fun rememberAkariOutlinedTextFieldState(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    // Solo recrear el estado cuando cambien los keys importantes
    return remember(value, onValueChange) {
        val variant = AkariTextFieldVariant.Outlined
        AkariTextFieldStateBuilder(value, onValueChange)
            .apply { variant { variant } }
            .apply(builder)
            .build()
    }
}

@Composable
fun rememberAkariSearchTextFieldState(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    // Solo recrear el estado cuando cambien los keys importantes
    return remember(value, onValueChange) {
        val variant = AkariTextFieldVariant.Search
        AkariTextFieldStateBuilder(value, onValueChange)
            .apply { variant { variant } }
            .apply(builder)
            .build()
    }
}

@Composable
fun rememberAkariVariantTextFieldState(
    variant: AkariTextFieldVariant,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    builder: AkariTextFieldStateBuilder.() -> Unit = {}
): AkariTextFieldState {
    // Solo recrear el estado cuando cambien los keys importantes
    return remember(value, onValueChange) {
        AkariTextFieldStateBuilder(value, onValueChange)
            .apply { variant { variant } }
            .apply(builder)
            .build()
    }
}