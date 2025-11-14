package com.akari.uicomponents.textFields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldTheme
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldVisuals
import com.akari.uicomponents.textFields.state.AkariTextFieldState

/**
 * A highly customizable text field component for the Akari design system.
 *
 * This composable provides a foundational text input field that can be extensively configured
 * through the [AkariTextFieldState]. It handles various visual states (focused, unfocused,
 * disabled, error), animations, and integrates seamlessly with icons, prefixes, suffixes,
 * and supporting text.
 *
 * The behavior and appearance are almost entirely controlled by the [state] parameter,
 * promoting a declarative and state-driven UI approach.
 *
 * Example usage:
 * ```
 * var value by remember { mutableStateOf(TextFieldValue(name)) }
 * val focusManager = LocalFocusManager.current
 * val firsFocusRequester = remember { FocusRequester() }
 * val shapes = MaterialTheme.shapes
 * val textFieldState = rememberAkariTextFieldState(
 *     value = value,
 *     onValueChange = { value = it },
 *     builder = {
 *        placeholder = "Enter your name"
 *        isError = text.length < 3
 *        style {
 *             shape = shapes.large
 *        }
 *        supportingText = { if (isError) Text("Name is too short") }
 *     }
 * )
 *
 * AkariTextField(state = textFieldState)
 * ```
 *
 * @param modifier The [Modifier] to be applied to the text field layout.
 * @param state The [AkariTextFieldState] that holds all the configuration and state
 *              for this text field, including its value, styling, and behavior.
 *              Use `rememberAkariTextFieldState` to create and remember an instance of this state.
 *
 * @see AkariTextFieldState
 * @see com.akari.uicomponents.textFields.rememberAkariTextFieldState
 */
@Composable
fun AkariTextField(
    modifier: Modifier = Modifier,
    state: AkariTextFieldState,
) {
    val focusRequester = state.focusRequester ?: remember { FocusRequester() }

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var hasAutoSelected by rememberSaveable { mutableStateOf(false) }

    val akariStyle = state.style
    val akariBehavior = state.behavior

    val theme = AkariTextFieldTheme.Material

    val visualState by remember{
        derivedStateOf {
            when{
                state.isError -> AkariTextFieldVisuals.State.ERROR
                isFocused -> AkariTextFieldVisuals.State.FOCUSED
                !akariBehavior.enabled -> AkariTextFieldVisuals.State.DISABLED
                else -> AkariTextFieldVisuals.State.UNFOCUSED
            }
        }
    }

    // Defaults composables (si vienen nulos en state)
    val colors = akariStyle.colors ?: theme.colors()
    val cursorBrush: Brush = akariStyle.cursorBrush ?: theme.cursorBrush()

    val akariVisuals = remember(colors, visualState) {
        AkariTextFieldVisuals(colors, visualState)
    }

    val shape = akariStyle.shape ?: theme.shape()
    val textStyle = akariStyle.textStyle
    val borderThickness = akariStyle.borderThickness ?: 0.dp

    val borderColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.BORDER)

    val backgroundColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.BACKGROUND)

    val textColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.TEXT)

    val placeholderColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.PLACEHOLDER)

    val supportingTextColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.SUPPORTING)

    Column(
        modifier = modifier.semantics {
            if (state.isError) error(state.supportingText?.toString() ?: "")
        }
    ) {
        state.label?.invoke()
        // Contenedor visual
        Box(
            modifier = Modifier
                .akariTextFieldContainer(
                    shape = shape,
                    backgroundColor = backgroundColor,
                    borderThickness = borderThickness,
                    borderColor = borderColor,
                    minHeight = akariStyle.minHeightTextField,
                    contentPadding = akariStyle.contentPadding
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Icono inicial
                state.leadingIcon?.invoke(isFocused)

                // Prefijo
                state.prefix?.invoke()

                // TextField principal
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = akariStyle.minHeightInnerTextField),
                    contentAlignment = Alignment.CenterStart
                ) {
                    this@Row.AnimatedVisibility(
                        visible = state.value.text.isEmpty() && state.placeholder != null,
                        enter = fadeIn(animationSpec = tween(150)),
                        exit = fadeOut(animationSpec = tween(150))
                    ) {
                        Text(
                            text = state.placeholder.orEmpty(),
                            color = placeholderColor,
                            style = textStyle
                        )
                    }
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester)
                            .focusProperties(state.focusProperties)
                            .onFocusChanged { focusState ->
                                if (focusState.isFocused) {
                                    if (akariBehavior.autoSelectOnFocus && !hasAutoSelected) {
                                        state.onValueChange(
                                            state.value.copy(
                                                selection = TextRange(0, state.valueText.length)
                                            )
                                        )
                                        hasAutoSelected = true
                                    }
                                } else {
                                    hasAutoSelected = false
                                }
                            },
                        value = state.value,
                        onValueChange = state.onValueChange,
                        interactionSource = interactionSource,
                        enabled = akariBehavior.enabled,
                        readOnly = akariBehavior.readOnly,
                        textStyle = textStyle.copy(color = textColor),
                        keyboardOptions = akariBehavior.keyboardOptions,
                        keyboardActions = akariBehavior.keyboardActions,
                        singleLine = akariBehavior.singleLine,
                        maxLines = akariBehavior.maxLines,
                        minLines = akariBehavior.minLines,
                        visualTransformation = akariBehavior.visualTransformation,
                        onTextLayout = state.onTextLayout,
                        cursorBrush = cursorBrush,
                    )
                }

                // Sufijo
                state.suffix?.invoke()

                // Icono final
                state.trailingIcon?.invoke(isFocused)
            }
        }

        // Texto de soporte o error
        state.supportingText?.let { supportingText ->
            Spacer(modifier = Modifier.height(4.dp))
            CompositionLocalProvider(LocalContentColor provides supportingTextColor) {
                supportingText()
            }
        }
    }

    LaunchedEffect(akariBehavior.requestFocus) {
        if (!akariBehavior.readOnly && akariBehavior.requestFocus) {
            focusRequester.requestFocus()
        }
    }
}

private fun Modifier.akariTextFieldContainer(
    shape: Shape,
    backgroundColor: Color,
    borderThickness: Dp,
    borderColor: Color,
    minHeight: Dp,
    contentPadding: PaddingValues
) = this
    .clip(shape)
    .background(backgroundColor)
    .border(width = borderThickness, color = borderColor, shape = shape)
    .heightIn(min = minHeight)
    .padding(contentPadding)