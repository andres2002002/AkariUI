package com.akari.uicomponents.textFields.internalConfig

import android.util.Log
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.akari.uicomponents.textFields.AkariTextField
import com.akari.uicomponents.textFields.config.AkariTextFieldConfig
import com.akari.uicomponents.textFields.config.AkariTextFieldStyle
import com.akari.uicomponents.textFields.internalConfig.AkariTextFieldDefaults.DecorationBox
import kotlin.let

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
        @Composable get() = MaterialTheme.shapes.large
    /** Default text style for an [AkariTextField]. */
    val textStyle: TextStyle
        @Composable get() = LocalTextStyle.current

    /**
     * The default min height applied to an [AkariTextField]. Note that you can override it by
     * applying Modifier.heightIn directly on a text field.
     */
    val MinHeight = 40.dp

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
    private val LabelPositionAnimationSpec = tween<IntOffset>(250)
    private val FadeAnimationSpec = tween<Float>(150)

    private const val LABEL_ID = "label"
    private const val LEADING_ID = "leading"
    private const val PREFIX_ID = "prefix"
    private const val TEXT_FIELD_ID = "textField"
    private const val PLACEHOLDER_ID = "placeholder"
    private const val SUFFIX_ID = "suffix"
    private const val TRAILING_ID = "trailing"

    /**
     * The default implementation of the decoration box for an [AkariTextField].
     *
     * This composable is responsible for drawing the visual elements of the text field,
     * such as the container, border, label, placeholder, and icons. It orchestrates the
     * layout and appearance of these components based on the text field's state and style.
     *
     * @param modifier The modifier to be applied to the decoration box.
     * @param innerTextField A composable that renders the actual text input area.
     * @param isTextEmpty A boolean indicating whether the text field's input is empty.
     * @param config The configuration for the text field.
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
        isTextEmpty: Boolean,
        config: AkariTextFieldConfig,
        isFocused: Boolean,
        akariVisuals: AkariTextFieldVisuals,
        shape: Shape,
        useInternalLabel: Boolean
    ) {
        val akariStyle = config.style
        val slots = config.slots
        val padding = config.padding

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

        val isLabelFloating = isFocused || !isTextEmpty

        var labelWidth by remember { mutableIntStateOf(0) }

        DebugStateIdentity(config)
        TextFieldLayout(
            modifier = modifier,
            label = if (useInternalLabel && slots.label != null) {
                {
                    InternalLabel(
                        label = slots.label,
                        labelColor = labelColor,
                        containerColor = backgroundColor,
                        isFloating = isLabelFloating,
                        labelPadding = padding.label
                    )
                }
            } else null,
            leadingIcon = slots.leadingIcon?.let {
                {
                    Wrapper(color = leadingIconColor, padding = padding.leadingIcon) {
                        it(isFocused)
                    }
                }
            },
            prefix = slots.prefix?.let {
                {
                    Wrapper(color = prefixColor, padding = padding.prefix) {
                        it()
                    }
                }
            },
            textField = {
                CompositionLocalProvider(LocalContentColor provides textColor) {
                    innerTextField()
                }
            },
            placeholder = slots.placeholder?.let {
                {
                    val showPlaceholder = remember(isTextEmpty, useInternalLabel, isLabelFloating) {
                        isTextEmpty && (!useInternalLabel || isLabelFloating)

                    }

                    val placeholderAlpha by animateFloatAsState(
                        if (showPlaceholder) 1f else 0f,
                        animationSpec = FadeAnimationSpec,
                        label = "placeholderAlpha"
                    )

                    if (placeholderAlpha > 0f) {
                        Box(Modifier.graphicsLayer { alpha = placeholderAlpha }) {
                            CompositionLocalProvider(
                                LocalContentColor provides placeholderColor,
                            ) {
                                it()
                            }
                        }
                    }
                }
            },
            suffix = slots.suffix?.let {
                {
                    Wrapper(color = suffixColor, padding = padding.suffix) {
                        it()
                    }
                }
            },
            trailingIcon = slots.trailingIcon?.let {
                {
                    Wrapper(color = trailingIconColor, padding = padding.trailingIcon) {
                        it(isFocused)
                    }
                }
            },
            isLabelFloating = isLabelFloating,
            textFieldPadding = padding.mainContent,
            shape = shape,
            borderColor = borderColor,
            backgroundColor = backgroundColor,
            borderThickness = borderThickness,
            labelWidth = labelWidth,
            contentPadding = padding.content
        )
    }

    @Composable
    fun DebugStateIdentity(config: AkariTextFieldConfig) {
        SideEffect {
            Log.d(
                "StateIdentity",
                "config hash=${System.identityHashCode(config)}"
            )
            Log.d(
                "STYLE",
                "style=${System.identityHashCode(config.style)} " +
                        "behavior=${System.identityHashCode(config.behavior)}"
            )
        }
    }
    @Composable
    private fun TextFieldLayout(
        modifier: Modifier = Modifier,
        label: (@Composable () -> Unit)?,
        leadingIcon: (@Composable () -> Unit)?,
        prefix: (@Composable () -> Unit)?,
        textField: @Composable () -> Unit,
        placeholder: (@Composable () -> Unit)?,
        suffix: (@Composable () -> Unit)?,
        trailingIcon: (@Composable () -> Unit)?,
        isLabelFloating: Boolean,
        textFieldPadding: PaddingValues,
        shape: Shape,
        borderColor: Color,
        backgroundColor: Color,
        borderThickness: Dp,
        labelWidth: Int,
        contentPadding: PaddingValues
    ) {
        // Estados para las posiciones calculadas
        var labelTargetPosition by remember { mutableStateOf(IntOffset.Zero) }

        // Animación de las posiciones
        val animatedLabelPosition by animateIntOffsetAsState(
            targetValue = labelTargetPosition,
            animationSpec = LabelPositionAnimationSpec,
            label = "labelPosition"
        )

        var borderPath = remember { Path() }
        val cutPath = remember { Path() }
        val finalPath = remember { Path() }

        val layoutDirection = LocalLayoutDirection.current

        Layout(
            modifier = modifier
                // Dibujar el border con corte para el label
                .drawWithContent {
                    // Primero dibujar el background
                    drawRect(color = backgroundColor)

                    // Obtener el outline del shape para extraer los corner radius
                    val outline = shape.createOutline(size, layoutDirection, this)
                    val borderWidthPx = borderThickness.toPx()

                    // Construir borderPath reutilizando 'borderPath' Path ya existente
                    borderPath.reset()
                    when (outline) {
                        is Outline.Rounded -> {
                            val r = outline.roundRect.topLeftCornerRadius
                            borderPath.addRoundRect(
                                RoundRect(
                                    rect = Rect(
                                        offset = Offset(borderWidthPx / 2, borderWidthPx / 2),
                                        size = Size(size.width - borderWidthPx, size.height - borderWidthPx)
                                    ),
                                    cornerRadius = r
                                )
                            )
                        }
                        is Outline.Rectangle -> {
                            borderPath.addRect(
                                Rect(
                                    offset = Offset(borderWidthPx / 2, borderWidthPx / 2),
                                    size = Size(size.width - borderWidthPx, size.height - borderWidthPx)
                                )
                            )
                        }
                        is Outline.Generic -> {
                            borderPath = outline.path
                        }
                    }

                    if (isLabelFloating && labelWidth > 0) {
                        val contentPaddingStartPx = contentPadding.calculateLeftPadding(layoutDirection).toPx()
                        val labelPaddingHorizontal = 4.dp.toPx()
                        val cutStartX = contentPaddingStartPx - labelPaddingHorizontal
                        val cutWidth = (labelWidth * 0.75f) + (labelPaddingHorizontal * 2)
                        val cutHeight = borderWidthPx * 3

                        // Construir el rect de corte en cutPath
                        cutPath.reset()
                        cutPath.addRect(
                            Rect(
                                offset = Offset(cutStartX, -cutHeight / 2),
                                size = Size(cutWidth, cutHeight)
                            )
                        )

                        // finalPath = borderPath - cutPath (reutilizando finalPath)
                        finalPath.reset()
                        finalPath.op(borderPath, cutPath, PathOperation.Difference)

                        drawPath(
                            path = finalPath,
                            color = borderColor,
                            style = Stroke(width = borderWidthPx)
                        )
                    } else {
                        drawPath(
                            path = borderPath,
                            color = borderColor,
                            style = Stroke(width = borderWidthPx)
                        )
                    }

                    drawContent()
                }
                .padding(contentPadding)
                .defaultMinSize(minWidth = MinWidth, minHeight = MinHeight),
            content = {
                label?.let {
                    Box(
                        Modifier
                            .layoutId(LABEL_ID)
                            .zIndex(1f)
                            .offset { animatedLabelPosition }
                    ) {
                        it()
                    }
                }
                leadingIcon?.let { Box(Modifier.layoutId(LEADING_ID)) { it() } }
                prefix?.let { Box(Modifier.layoutId(PREFIX_ID)) { it() } }
                Box(
                    Modifier.layoutId(TEXT_FIELD_ID),
                    contentAlignment = Alignment.Center
                ) { textField() }
                placeholder?.let {
                    Box(
                        Modifier.layoutId(PLACEHOLDER_ID),
                        contentAlignment = Alignment.Center
                    ) { it() }
                }
                suffix?.let { Box(Modifier.layoutId(SUFFIX_ID)) { it() } }
                trailingIcon?.let { Box(Modifier.layoutId(TRAILING_ID)) { it() } }
            }
        ) { measurables, constraints ->
            val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)
            val measurableMap = measurables.associateBy { it.layoutId }

            // Medir todos los componentes
            val leadingPlaceable = measurableMap[LEADING_ID]?.measure(looseConstraints)
            val trailingPlaceable = measurableMap[TRAILING_ID]?.measure(looseConstraints)
            val prefixPlaceable = measurableMap[PREFIX_ID]?.measure(looseConstraints)
            val suffixPlaceable = measurableMap[SUFFIX_ID]?.measure(looseConstraints)

            // calculos de paddings una sola vez
            val leftPad = textFieldPadding.calculateLeftPadding(layoutDirection).roundToPx()
            val rightPad = textFieldPadding.calculateRightPadding(layoutDirection).roundToPx()
            val topPad = textFieldPadding.calculateTopPadding().roundToPx()
            val bottomPad = textFieldPadding.calculateBottomPadding().roundToPx()
            val horizontalPadding = leftPad + rightPad

            val occupiedWidth = (leadingPlaceable?.width ?: 0) +
                    (trailingPlaceable?.width ?: 0) +
                    (prefixPlaceable?.width ?: 0) +
                    (suffixPlaceable?.width ?: 0) +
                    horizontalPadding
            val textFieldConstraints = constraints.copy(
                minWidth = 0,
                maxWidth = (constraints.maxWidth - occupiedWidth).coerceAtLeast(0)
            )

            val textFieldPlaceable = measurableMap[TEXT_FIELD_ID]!!.measure(textFieldConstraints)
            val placeholderPlaceable = measurableMap[PLACEHOLDER_ID]?.measure(textFieldConstraints)
            val labelPlaceable = measurableMap[LABEL_ID]?.measure(looseConstraints)


            // Calcular altura del contenedor
            val contentHeight = maxOf(
                textFieldPlaceable.height,
                leadingPlaceable?.height ?: 0,
                trailingPlaceable?.height ?: 0
            ) + topPad + bottomPad

            val width = constraints.constrainWidth(
                (leadingPlaceable?.width ?: 0) +
                        (prefixPlaceable?.width ?: 0) +
                        textFieldPlaceable.width +
                        (suffixPlaceable?.width ?: 0) +
                        (trailingPlaceable?.width ?: 0) +
                        horizontalPadding
            )
            val height = constraints.constrainHeight(contentHeight)

            layout(width, height) {
                // Posicionar componentes horizontalmente
                var currentX = 0
                val centerY = height / 2

                // Leading icon
                leadingPlaceable?.let {
                    it.placeRelative(currentX, centerY - (it.height/2))
                    currentX += it.width
                }

                // Prefix
                prefixPlaceable?.let {
                    it.placeRelative(currentX, centerY - (it.height/2))
                    currentX += it.width
                }

                // Text field padding left
                currentX += textFieldPadding.calculateLeftPadding(layoutDirection).roundToPx()

                val textFieldX = currentX
                // TextField y Placeholder centrados verticalmente
                val textFieldY = centerY - (textFieldPlaceable.height / 2)

                // TextField
                textFieldPlaceable.placeRelative(textFieldX, textFieldY)

                // Placeholder (misma posición que el texto, también centrado)
                placeholderPlaceable?.placeRelative(textFieldX, textFieldY)

                currentX += textFieldPlaceable.width +
                        textFieldPadding.calculateRightPadding(layoutDirection).roundToPx()

                // Suffix
                suffixPlaceable?.let {
                    it.placeRelative(currentX, centerY - (it.height/2))
                    currentX += it.width
                }

                // Trailing icon
                trailingPlaceable?.let {
                    it.placeRelative(currentX, centerY - (it.height/2))
                }

                // Label - calcular posición objetivo y aplicar offset animado
                labelPlaceable?.let {
                    val labelY = if (isLabelFloating) {
                        // Cuando flota, va arriba centrado verticalmente en su mitad
                        - (it.height * 3/4)
                    } else {
                        // Cuando no flota, centrado verticalmente en el contenedor
                        centerY - (it.height / 2)
                    }
                    val labelX = if (isLabelFloating) {
                        0
                    } else {
                        textFieldX
                    }

                    // Actualizar posición objetivo
                    labelTargetPosition = IntOffset(labelX, labelY)

                    // Colocar en 0,0 porque el offset animado lo moverá
                    it.placeRelative(0, 0)
                }
            }
        }
    }

    @Composable
    private fun Wrapper(
        color: Color,
        padding: PaddingValues,
        content: @Composable () -> Unit
    ) {
        CompositionLocalProvider(LocalContentColor provides color) {
            Box(modifier = Modifier.padding(padding)) { content() }
        }
    }
    @Composable
    private fun InternalLabel(
        label: @Composable () -> Unit,
        labelPadding: PaddingValues,
        labelColor: Color,
        containerColor: Color,
        isFloating: Boolean
    ) {
        val colorScheme = MaterialTheme.colorScheme

        val transition = updateTransition(
            targetState = isFloating,
            label = "LabelTransition"
        )

        val labelScale by transition.animateFloat(
            transitionSpec = { LabelAnimationSpec },
            label = "labelScale"
        ) { floating ->
            if (floating) 0.75f else 1f
        }

        val isInvisible = remember(containerColor) {
                containerColor == Color.Transparent || containerColor.alpha < 0.2f
        }

        val shape = MaterialTheme.shapes.extraSmall

        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = labelScale
                    scaleY = labelScale
                    transformOrigin = TransformOrigin(0f, 0.5f)
                }
        ) {
            CompositionLocalProvider(LocalContentColor provides labelColor) {
                val bgColor = if (isInvisible) colorScheme.surface else containerColor

                val backgroundModifier = remember(shape, bgColor, isFloating, labelPadding) {
                    Modifier
                        .clip(shape)
                        .background(bgColor)
                        .then(
                            if (isFloating) {
                                Modifier.padding(labelPadding)
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