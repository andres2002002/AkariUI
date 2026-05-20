package com.akari.uicomponents.scaffold

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@DslMarker
annotation class ScaffoldDsl

/**
 * Scope genérico para mapear cualquier tipo de SizeClass (S) a un componente (T).
 */
@ScaffoldDsl
class SizeScope<S, T>(
    private val map: MutableMap<S, T>
) {
    /**
     * Permite una sintaxis declarativa limpia.
     * Ejemplo: MySize.Compact provides { ... }
     */
    infix fun S.provides(value: T) {
        map[this] = value
    }

    /**
     * Utilidad para asignar el mismo valor a múltiples breakpoints.
     */
    fun anyOf(vararg sizes: S, value: T) {
        sizes.forEach { map[it] = value }
    }
}

@ScaffoldDsl
class AkariScaffoldConfigDsl<S> {

    /* -------- Configuración general -------- */

    private var gesturesEnabled: Boolean = true
    private var containerColor: Color = Color.Transparent
    private var contentColor: Color = Color.Unspecified
    private var snackbarHost: (@Composable () -> Unit)? = null

    /* -------- Configuración por tamaño -------- */

    private val topBars =
        mutableMapOf<S, @Composable () -> Unit>()

    private val bottomBars =
        mutableMapOf<S, @Composable () -> Unit>()

    private val fabs =
        mutableMapOf<S, @Composable () -> Unit>()

    private val fabPositions =
        mutableMapOf<S, FabPosition>()

    private val navigationRails =
        mutableMapOf<S, @Composable () -> Unit>()

    private val drawerSheets = mutableMapOf<S, @Composable (closeDrawer: () -> Unit) -> Unit>()

    fun enableGestures(enabled: Boolean) {
        gesturesEnabled = enabled
    }

    fun containerColor(color: Color) {
        containerColor = color
    }

    fun contentColor(color: Color) {
        contentColor = color
    }

    fun snackbarHost(host: @Composable () -> Unit) {
        snackbarHost = host
    }

    fun topBar(block: SizeScope<S, @Composable () -> Unit>.() -> Unit) {
        SizeScope(topBars).apply(block)
    }

    fun bottomBar(block: SizeScope<S, @Composable () -> Unit>.() -> Unit) {
        SizeScope(bottomBars).apply(block)
    }

    fun fab(block: SizeScope<S, @Composable () -> Unit>.() -> Unit) {
        SizeScope(fabs).apply(block)
    }

    fun fabPosition(block: SizeScope<S, FabPosition>.() -> Unit) {
        SizeScope(fabPositions).apply(block)
    }

    fun navigationRail(
        block: SizeScope<S, @Composable () -> Unit>.() -> Unit
    ) {
        SizeScope(navigationRails).apply(block)
    }

    fun drawerSheet(block: SizeScope<S, @Composable (closeDrawer: () -> Unit) -> Unit>.() -> Unit) {
        SizeScope(drawerSheets).apply(block)
    }

    fun build(): AkariScaffoldConfig<S> =
        AkariScaffoldConfig(
            gesturesEnabled = gesturesEnabled,
            containerColor = containerColor,
            contentColor = contentColor,
            snackbarHost = snackbarHost,
            topBars = topBars.toMap(),
            bottomBars = bottomBars.toMap(),
            fabs = fabs.toMap(),
            fabPositions = fabPositions.toMap(),
            navigationRails = navigationRails.toMap(),
            drawerSheets = drawerSheets
        )
}

@Composable
fun<S> rememberAkariScaffoldConfig(
    vararg keys: Any?,
    block: AkariScaffoldConfigDsl<S>.() -> Unit
): AkariScaffoldConfig<S> {
    return remember(*keys) {
        AkariScaffoldConfigDsl<S>()
            .apply(block)
            .build()
    }
}
