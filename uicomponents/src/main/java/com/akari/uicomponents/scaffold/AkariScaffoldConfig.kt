package com.akari.uicomponents.scaffold

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Configuración inmutable del AkariScaffold.
 */
@Immutable
class AkariScaffoldConfig<S> internal constructor(
    val gesturesEnabled: Boolean,
    val containerColor: Color,
    val contentColor: Color,
    val snackbarHost: (@Composable () -> Unit)?,

    private val topBars:
    Map<S, @Composable () -> Unit>,

    private val bottomBars:
    Map<S, @Composable () -> Unit>,

    private val fabs:
    Map<S, @Composable () -> Unit>,

    private val fabPositions:
    Map<S, FabPosition>,

    private val navigationRails:
    Map<S, @Composable () -> Unit>,

    private val drawerSheets: Map<S, @Composable (closeDrawer: () -> Unit) -> Unit>
) {

    /* -------------------- Access API -------------------- */

    /**
     * TopBar correspondiente al ancho actual.
     */
    @Composable
    fun topBar(size: S) =
        topBars[size]

    /**
     * BottomBar correspondiente al ancho actual.
     */
    @Composable
    fun bottomBar(size: S) =
        bottomBars[size]

    /**
     * FloatingActionButton correspondiente al ancho actual.
     */
    @Composable
    fun fab(size: S) =
        fabs[size]

    /**
     * Posición del FAB, con fallback seguro.
     */
    fun fabPosition(size: S): FabPosition =
        fabPositions[size] ?: FabPosition.End

    /**
     * NavigationRail correspondiente al ancho actual.
     */
    @Composable
    fun navigationRail(size: S) =
        navigationRails[size]

    /**
     * Drawer correspondiente al ancho actual.
     */
    fun drawerSheet(size: S) = drawerSheets[size]
}