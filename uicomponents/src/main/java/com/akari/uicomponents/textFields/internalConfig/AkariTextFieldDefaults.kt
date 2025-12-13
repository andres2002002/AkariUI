package com.akari.uicomponents.textFields.internalConfig

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
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
    /** Default text style for an [AkariTextField]. */
    val textStyle: TextStyle
        @Composable get() = LocalTextStyle.current

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

    // Specs de animación reutilizables (constantes en memoria)
    private val LabelAnimationSpec = tween<Float>(250)
    private val OffsetAnimationSpec = tween<Dp>(150)
    private val FadeAnimationSpec = tween<Float>(150)

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
        useInternalLabel: Boolean
    ) {
        val akariStyle = state.style
        val borderThickness = remember(isFocused, akariStyle) {
            if (isFocused) {
                akariStyle.focusedBorderThickness ?: FocusedBorderThickness
            } else {
                akariStyle.unfocusedBorderThickness ?: UnfocusedBorderThickness
            }
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

        val isLabelFloating = isFocused || state.value.text.isNotEmpty()

        // Coordenadas para posicionamiento preciso
        var containerOffset by remember { mutableStateOf(IntOffset.Zero) }
        var textFieldContentOffset by remember { mutableStateOf(IntOffset.Zero) }
        var textFieldContentSize by remember { mutableStateOf(IntSize.Zero) }

        val containerModifier = Modifier
            .zIndex(0f)
            .clip(shape)
            .background(backgroundColor)
            .border(width = borderThickness, color = borderColor, shape = shape)
            .padding(akariStyle.textFieldPadding.contentPadding)
            .defaultMinSize(minWidth = MinWidth, minHeight = MinHeight)


        Box(
            modifier = Modifier
                .onGloballyPositioned { coordinates ->
                    containerOffset = coordinates.positionInParent().round()
                }
        ) {
            // Label interno flotante
            if (useInternalLabel && state.label != null) {
                InternalLabel(
                    label = state.label,
                    akariStyle = akariStyle,
                    labelColor = labelColor,
                    containerColor = backgroundColor,
                    isFloating = isLabelFloating,
                    containerOffset = containerOffset,
                    textFieldContentOffset = textFieldContentOffset,
                    textFieldContentSize = textFieldContentSize
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
                            .padding(akariStyle.textFieldPadding.mainContentPadding)
                            .onGloballyPositioned { coordinates ->
                                textFieldContentOffset = coordinates.positionInParent().round()
                                textFieldContentSize = coordinates.size
                            },
                        contentAlignment = Alignment.CenterStart
                    ) {
                        // Placeholder
                        val showPlaceholder  = state.value.text.isEmpty() &&
                                (!useInternalLabel || isLabelFloating)

                        val placeholderAlpha by animateFloatAsState(
                            if (showPlaceholder) 1f else 0f,
                            animationSpec = FadeAnimationSpec,
                            label = "placeholderAlpha"
                        )

                        if (state.placeholder != null && placeholderAlpha > 0f) {
                            Box(Modifier.graphicsLayer { alpha = placeholderAlpha }) {
                                CompositionLocalProvider(
                                    LocalContentColor provides placeholderColor,
                                ) {
                                    state.placeholder()
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
        CompositionLocalProvider(
            LocalContentColor provides color
        ) {
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
        CompositionLocalProvider(
            LocalContentColor provides color,
        ) {
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
        isFloating: Boolean,
        containerOffset: IntOffset,
        textFieldContentOffset: IntOffset,
        textFieldContentSize: IntSize
    ) {
        val colorScheme = MaterialTheme.colorScheme
        var labelHeight by remember { mutableIntStateOf(0) }
        val density = LocalDensity.current

        val transition = updateTransition(
            targetState = isFloating,
            label = "LabelTransition"
        )

        val targetX = with(density) {
            if (isFloating) 0.dp
            else (textFieldContentOffset.x - containerOffset.x).toDp()
        }

        val targetY = with(density) {
            if (isFloating) {
                val totalHeight = textFieldContentSize.height +
                        akariStyle.textFieldPadding.contentPadding.calculateTopPadding().toPx() +
                        akariStyle.textFieldPadding.contentPadding.calculateBottomPadding().toPx()
                -(totalHeight / 2).toInt().toDp()
            } else {
                val textFieldCenterY = textFieldContentOffset.y - containerOffset.y +
                        (textFieldContentSize.height / 2)
                val labelCenterY = labelHeight / 2
                (textFieldCenterY - labelCenterY).toDp()
            }
        }
        val offsetY by animateDpAsState(
            targetValue = targetY,
            animationSpec = OffsetAnimationSpec,
            label = "labelOffsetY"
        )

        val offsetX by animateDpAsState(
            targetValue = targetX,
            animationSpec = OffsetAnimationSpec,
            label = "labelOffsetX"
        )

        val labelScale by transition.animateFloat(
            transitionSpec = { LabelAnimationSpec },
            label = "labelScale"
        ) { isFloating ->
            if (isFloating) 0.75f else 1f
        }

        val isInvisible = containerColor == Color.Transparent || containerColor.alpha < 0.2f


        val shape = MaterialTheme.shapes.extraSmall

        // Modifier base del label
        val labelModifier = Modifier
            .zIndex(1f)
            .offset(x = offsetX, y = offsetY)
            .padding(akariStyle.textFieldPadding.contentPadding)
            .graphicsLayer {
                scaleX = labelScale
                scaleY = labelScale
                transformOrigin = TransformOrigin(0f, 0f)
            }
        Box(
            modifier = labelModifier.onSizeChanged { size ->
                labelHeight  = size.height
            }
        ) {

            CompositionLocalProvider(
                LocalContentColor provides labelColor
            ) {
                val backgroundModifier = Modifier
                    .clip(shape)
                    .background(if (isInvisible) colorScheme.surface else containerColor)
                    .then(
                        if (isFloating) {
                            Modifier.padding(akariStyle.textFieldPadding.labelPadding)
                        } else {
                            Modifier
                        }
                    )

                Box(modifier = backgroundModifier) {
                    label()
                }
            }
        }
    }
}