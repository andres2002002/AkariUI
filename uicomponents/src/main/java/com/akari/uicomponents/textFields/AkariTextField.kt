package com.akari.uicomponents.textFields

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextRange
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldDefaults
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

    val visualState = remember( isFocused, state.isError, akariBehavior.enabled){
        when {
            state.isError -> AkariTextFieldVisuals.VisualState.ERROR
            isFocused -> AkariTextFieldVisuals.VisualState.FOCUSED
            !akariBehavior.enabled -> AkariTextFieldVisuals.VisualState.DISABLED
            else -> AkariTextFieldVisuals.VisualState.UNFOCUSED
        }
    }

    // Defaults composables (si vienen nulos en state)
    val colors = akariStyle.colors ?: AkariTextFieldDefaults.colors()

    val akariVisuals = remember(colors, visualState) {
        AkariTextFieldVisuals(colors, visualState)
    }

    val shape = akariStyle.shape ?: AkariTextFieldDefaults.shape
    val textStyle = akariStyle.textStyle?: AkariTextFieldDefaults.textStyle
    val textColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.TEXT)
    val supportingTextColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.SUPPORTING)
    val labelColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.LABEL)
    val cursorColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.CURSOR)

    val cursorBrush = akariStyle.cursorBrush?: SolidColor(cursorColor)

    // Determinar si usar label interno o externo
    val useInternalLabel = state.labelBehavior == AkariLabelBehavior.FLOATING

    Column(
        modifier = Modifier
            .semantics { if (state.isError) error(state.supportingText?.toString() ?: "") }
            .padding(akariStyle.textFieldPadding.externalContentPadding)
    ) {
        if (!useInternalLabel) {
            state.label?.let { label ->
                Box(modifier = Modifier.padding(akariStyle.textFieldPadding.labelPadding)) {
                    CompositionLocalProvider(
                        LocalContentColor provides labelColor,
                        LocalTextStyle provides textStyle.copy(color = labelColor)
                    ) {
                        label()
                    }
                }
            }
        }

        CompositionLocalProvider(
            LocalTextSelectionColors provides colors.textSelectionColors,
            LocalTextStyle provides textStyle.copy(color = textColor)
        ) {
            BasicTextField(
                value = state.value,
                onValueChange = state.onValueChange,
                modifier = Modifier
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
                decorationBox = { innerTextField ->
                    AkariTextFieldDefaults.DecorationBox(
                        modifier = modifier,
                        innerTextField = innerTextField,
                        state = state,
                        isFocused = isFocused,
                        akariVisuals = akariVisuals,
                        shape = shape,
                        useInternalLabel = useInternalLabel
                    )
                }
            )
        }

        AnimatedVisibility(
            visible = state.value.text.isEmpty() && state.placeholder != null,
            enter = fadeIn(animationSpec = tween(150)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            // Texto de soporte o error
            state.supportingText?.let { supportingText ->
                Box(modifier = Modifier.padding(akariStyle.textFieldPadding.supportingTextPadding)){
                    CompositionLocalProvider(LocalContentColor provides supportingTextColor) {
                        supportingText()
                    }
                }
            }
        }
    }

    LaunchedEffect(akariBehavior.requestFocus) {
        if (!akariBehavior.readOnly && akariBehavior.requestFocus) {
            focusRequester.requestFocus()
        }
    }
}