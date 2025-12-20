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
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldDefaults
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldVisuals

/**
 * A highly customizable text field component for the Akari design system.
 *
 * This composable provides a robust text input field that can be extensively configured
 * through the [AkariTextFieldConfig]. It handles various visual states (focused, unfocused,
 * disabled, error), animations, and integrates seamlessly with labels, placeholders,
 * suffixes, prefixes, and supporting text.
 *
 * The behavior and appearance are driven by the explicit parameters passed to the composable
 * (such as [value], [onValueChange], [isError]) and the structured [config] object.
 *
 * Example usage:
 * ```
 * var value by remember { mutableStateOf(TextFieldValue("")) }
 * val focusRequester = remember { FocusRequester() }
 *
 * AkariTextField(
 *     value = value,
 *     onValueChange = { value = it },
 *     isError = value.text.length < 3,
 *     config = AkariTextFieldConfig(
 *         style = AkariStyleConfig(
 *             shape = RoundedCornerShape(8.dp)
 *         ),
 *         slots = AkariSlots(
 *             placeholder = { Text("Enter your name") },
 *             supportingText = { if (value.text.length < 3) Text("Name is too short") }
 *         ),
 *         behavior = AkariBehaviorConfig(
 *             autoSelectOnFocus = true
 *         )
 *     ),
 *     focusRequester = focusRequester
 * )
 * ```
 *
 * @param modifier The [Modifier] to be applied to the decoration box surrounding the text field content.
 * @param value The input [TextFieldValue] to be shown in the text field.
 * @param onValueChange The callback invoked when the input [TextFieldValue] changes.
 * @param isError Whether the text field is in an error state.
 * @param enabled Whether the text field is enabled.
 * @param config The [AkariTextFieldConfig] for the text field.
 * @param focusRequester The [FocusRequester] to be applied to the text field.
 * @param onTextLayout The callback invoked when the text layout occurs.
 *
 */
@Composable
fun AkariTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit = {},
    isError: Boolean = false,
    enabled: Boolean = true,
    config: AkariTextFieldConfig = AkariTextFieldConfig(),
    focusRequester: FocusRequester = remember { FocusRequester() },
    onTextLayout: (TextLayoutResult) -> Unit  = {}
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    var hasAutoSelected by rememberSaveable { mutableStateOf(false) }

    val akariStyle = config.style
    val akariBehavior = config.behavior
    val akariPadding = config.padding
    val akariSlots = config.slots

    val visualState = remember( isFocused, isError, enabled){
        when {
            isError -> AkariTextFieldVisuals.VisualState.ERROR
            isFocused -> AkariTextFieldVisuals.VisualState.FOCUSED
            !enabled -> AkariTextFieldVisuals.VisualState.DISABLED
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
    val useInternalLabel = akariBehavior.labelBehavior == AkariLabelBehavior.FLOATING

    Column(
        modifier = Modifier
            .semantics { if (isError) error(akariSlots.supportingText?.toString() ?: "") }
            .padding(akariPadding.externalContent)
    ) {
        if (!useInternalLabel) {
            akariSlots.label?.let { label ->
                Box(modifier = Modifier.padding(akariPadding.label)) {
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
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .focusProperties(config.focusProperties)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) {
                            if (akariBehavior.autoSelectOnFocus && !hasAutoSelected) {
                                onValueChange(
                                    value.copy(
                                        selection = TextRange(0, value.text.length)
                                    )
                                )
                                hasAutoSelected = true
                            }
                        } else {
                            hasAutoSelected = false
                        }
                    },
                interactionSource = interactionSource,
                enabled = enabled,
                readOnly = akariBehavior.readOnly,
                textStyle = textStyle.copy(color = textColor),
                keyboardOptions = akariBehavior.keyboardOptions,
                keyboardActions = akariBehavior.keyboardActions,
                singleLine = akariBehavior.singleLine,
                maxLines = akariBehavior.normalizedMaxLines,
                minLines = akariBehavior.normalizedMinLines,
                visualTransformation = akariBehavior.visualTransformation,
                onTextLayout = onTextLayout,
                cursorBrush = cursorBrush,
                decorationBox = { innerTextField ->
                    AkariTextFieldDefaults.DecorationBox(
                        modifier = modifier,
                        innerTextField = innerTextField,
                        isTextEmpty = value.text.isEmpty(),
                        config = config,
                        isFocused = isFocused,
                        akariVisuals = akariVisuals,
                        shape = shape,
                        useInternalLabel = useInternalLabel
                    )
                }
            )
        }

        AnimatedVisibility(
            visible = value.text.isEmpty() && akariSlots.placeholder != null,
            enter = fadeIn(animationSpec = tween(150)),
            exit = fadeOut(animationSpec = tween(150))
        ) {
            // Texto de soporte o error
            akariSlots.supportingText?.let { supportingText ->
                Box(modifier = Modifier.padding(akariPadding.supportingText)){
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