package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.akari.uicomponents.textFields.AkariTextFieldStyle
import com.akari.uicomponents.textFields.state.AkariTextFieldState
import com.akari.uicomponents.textFields.AkariTextField

/**
 * Object that contains default values and configurations used by the [AkariTextField].
 *
 * This object provides default values for properties like shape, dimensions, and border thickness.
 * It also includes the [DecorationBox] composable, which is responsible for drawing the visual
 * elements of the text field, such as the container, label, placeholder, and icons,
 * according to the provided state and style.
 */
@Immutable
object AkariTextFieldDefaults {

    /** Default shape for an [AkariTextField]. */
    val shape: Shape
        @Composable get() = OutlinedTextFieldDefaults.shape

    /**
     * The default min height applied to an [AkariTextField]. Note that you can override it by
     * applying Modifier.heightIn directly on a text field.
     */
    val MinHeight = 24.dp

    /**
     * The default min width applied to an [AkariTextField]. Note that you can override it by
     * applying Modifier.widthIn directly on a text field.
     */
    val MinWidth = 280.dp

    /** The default thickness of the border in [AkariTextField] in unfocused state. */
    val UnfocusedBorderThickness = 1.dp

    /** The default thickness of the border in [AkariTextField] in focused state. */
    val FocusedBorderThickness = 2.dp

    @Composable fun colors() = OutlinedTextFieldDefaults.colors()

    /**
     * The default implementation of the decoration box for an [AkariTextField].
     *
     * This composable is responsible for drawing the visual elements of the text field,
     * such as the container, border, label, placeholder, and icons. It orchestrates the
     * layout and appearance of these components based on the text field's state and style.
     *
     * @param modifier The modifier to be applied to the decoration box.
     * @param innerTextField A composable that renders the actual text input area.
     * @param state The current [AkariTextFieldState] which holds information about the
     *   text field's content, style, and associated composables like label or icons.
     * @param isFocused A boolean indicating whether the text field is currently focused.
     * @param akariVisuals An instance of [AkariTextFieldVisuals] that provides the dynamic
     *   colors for the different parts of the text field.
     * @param shape The shape of the text field's container.
     * @param textStyle The [androidx.compose.ui.text.TextStyle] to be applied to the input text and used
     *   as a base for the label.
     * @param useInternalLabel A boolean that determines whether to use the built-in floating
     *   label implementation. If `false`, the label is expected to be handled externally.
     */
    @Composable
    fun DecorationBox(
        modifier: Modifier = Modifier,
        innerTextField: @Composable () -> Unit,
        state: AkariTextFieldState,
        isFocused: Boolean,
        akariVisuals: AkariTextFieldVisuals,
        shape: Shape,
        textStyle: androidx.compose.ui.text.TextStyle,
        useInternalLabel: Boolean
    ) {
        val akariStyle = state.style
        val borderThickness = remember(isFocused, akariStyle) {
            if (isFocused) akariStyle.focusedBorderThickness ?: FocusedBorderThickness
            else akariStyle.unfocusedBorderThickness ?: UnfocusedBorderThickness
        }

        val borderColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.BORDER)
        val backgroundColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.CONTAINER)
        val placeholderColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.PLACEHOLDER)
        val labelColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.LABEL)
        val textColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.TEXT)
        val leadingIconColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.LEADING)
        val trailingIconColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.TRAILING)
        val prefixColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.PREFIX)
        val suffixColor by akariVisuals.animatedColor(AkariTextFieldVisuals.Component.SUFFIX)

        val isLabelFloating = remember(isFocused, state.value.text) {
            isFocused || state.value.text.isNotEmpty()
        }
        // Animación del label con spec compartida
        val labelAnimationSpec = remember { tween<Float>(250) }
        val labelScale by animateFloatAsState(
            targetValue = if (isLabelFloating) 0.75f else 1f,
            animationSpec = labelAnimationSpec,
            label = "labelScale"
        )

        // Modifier del container calculado una sola vez
        val containerModifier = remember(
            shape, backgroundColor, borderThickness, borderColor, akariStyle.textFieldPadding
        ) {
            Modifier
                .zIndex(0f)
                .clip(shape)
                .background(backgroundColor)
                .border(width = borderThickness, color = borderColor, shape = shape)
                .padding(akariStyle.textFieldPadding.contentPadding)
                .defaultMinSize(minWidth = MinWidth, minHeight = MinHeight)
        }

        Box(
            modifier = Modifier
        ) {
            // Label interno flotante
            if (useInternalLabel && state.label != null) {
                InternalLabel(
                    label = state.label,
                    akariStyle = akariStyle,
                    labelColor = labelColor,
                    containerColor = backgroundColor,
                    textStyle = textStyle,
                    labelScale = labelScale,
                    isFloating = isLabelFloating
                )
            }
            Box(
                modifier = modifier.then(containerModifier)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Leading icon
                    state.leadingIcon?.let { leadingIcon ->
                        IconWrapper(
                            color = leadingIconColor,
                            padding = akariStyle.textFieldPadding.leadingIconPadding,
                            content = { leadingIcon(isFocused) }
                        )
                    }

                    // Prefix
                    state.prefix?.let { prefix ->
                        ContentWrapper(
                            color = prefixColor,
                            padding = akariStyle.textFieldPadding.prefixPadding,
                            content = prefix
                        )
                    }

                    // Text field content
                    Box(
                        modifier = Modifier
                            .padding(akariStyle.textFieldPadding.mainContentPadding),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // Placeholder
                        val showPlaceholder by remember {
                            derivedStateOf {
                                state.value.text.isEmpty() &&
                                        state.placeholder != null &&
                                        (!useInternalLabel || isLabelFloating)
                            }
                        }

                        if (showPlaceholder) {
                            this@Row.AnimatedVisibility(
                                visible = state.value.text.isEmpty() && state.placeholder != null,
                                enter = fadeIn(animationSpec = tween(150)),
                                exit = fadeOut(animationSpec = tween(150))
                            ) {
                                state.placeholder?.let { placeholder ->
                                    CompositionLocalProvider(LocalContentColor provides placeholderColor) {
                                        placeholder()
                                    }
                                }
                            }
                        }

                        // El campo de texto real
                        CompositionLocalProvider(LocalContentColor provides textColor) {
                            innerTextField()
                        }
                    }

                    // Suffix
                    state.suffix?.let { suffix ->
                        ContentWrapper(
                            color = suffixColor,
                            padding = akariStyle.textFieldPadding.suffixPadding,
                            content = suffix
                        )
                    }

                    // Trailing icon
                    state.trailingIcon?.let { trailingIcon ->
                        IconWrapper(
                            color = trailingIconColor,
                            padding = akariStyle.textFieldPadding.trailingIconPadding,
                            content = { trailingIcon(isFocused) }
                        )
                    }
                }
            }
        }
    }

    // Componentes auxiliares para reducir duplicación
    @Composable
    private fun IconWrapper(
        color: Color,
        padding: PaddingValues,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            Box(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }

    @Composable
    private fun ContentWrapper(
        color: Color,
        padding: PaddingValues,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            Box(modifier = Modifier.padding(padding)) {
                content()
            }
        }
    }

    @Composable
    private fun InternalLabel(
        label: @Composable () -> Unit,
        akariStyle: AkariTextFieldStyle,
        labelColor: Color,
        containerColor: Color,
        textStyle: androidx.compose.ui.text.TextStyle,
        labelScale: Float,
        isFloating: Boolean
    ) {
        val colorScheme = MaterialTheme.colorScheme
        var textFieldHeight by remember { mutableStateOf(0.dp) }
        val density = LocalDensity.current

        // Calcular target solo cuando cambien las dependencias
        val target = remember(textFieldHeight, akariStyle.textFieldPadding) {
            (textFieldHeight + akariStyle.textFieldPadding.contentPadding.calculateTopPadding()) / 2
        }

        val offsetAnimationSpec = remember { tween<Dp>(150) }
        val offsetY by animateDpAsState(
            targetValue = if (isFloating) -target else 0.dp,
            animationSpec = offsetAnimationSpec,
            label = "labelOffsetY"
        )
        val isInvisible = remember(containerColor) {
            containerColor == Color.Transparent || containerColor.alpha < 0.2f
        }

        val shape = MaterialTheme.shapes.extraSmall

        // Modifier base del label
        val labelModifier = remember(
            offsetY, labelScale, akariStyle.textFieldPadding, density, textFieldHeight
        ) {
            Modifier
                .zIndex(1f)
                .offset(y = offsetY)
                .padding(akariStyle.textFieldPadding.contentPadding)
                .graphicsLayer {
                    scaleX = labelScale
                    scaleY = labelScale
                    transformOrigin = TransformOrigin(0f, 0.0f)
                }
        }
        Box(
            modifier = labelModifier.onSizeChanged { size ->
                textFieldHeight = with(density) { size.height.toDp() }
            }
        ) {
            val effectiveLabelColor = remember(labelColor) { labelColor }
            val effectiveTextStyle = remember(textStyle, labelColor) {
                textStyle.copy(color = labelColor)
            }

            CompositionLocalProvider(
                LocalContentColor provides effectiveLabelColor,
                LocalTextStyle provides effectiveTextStyle
            ) {
                val backgroundModifier = remember(isInvisible, containerColor, isFloating, akariStyle) {
                    Modifier
                        .clip(shape)
                        .background(
                            if (isInvisible) colorScheme.surface
                            else containerColor
                        )
                        .then(
                            if (isFloating) {
                                Modifier.padding(akariStyle.textFieldPadding.labelPadding)
                            } else {
                                Modifier
                            }
                        )
                }

                Box(modifier = backgroundModifier) {
                    label()
                }
            }
        }
    }
}