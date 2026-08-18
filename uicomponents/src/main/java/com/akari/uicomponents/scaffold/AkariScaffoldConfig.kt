package com.akari.uicomponents.scaffold

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Configuración inmutable del AkariScaffold.
 *
 * IMPORTANTE: [S] debe ser un tipo verdaderamente estable (enum o data class
 * con propiedades `val` inmutables).
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

    /**
     * true si el DSL definió al menos un drawerSheet para algún tamaño.
     * Calculado una única vez en construcción, no depende de currentSize.
     * Usado por AkariScaffold para decidir si envuelve con
     * ModalNavigationDrawer, manteniendo esa decisión estable durante
     * toda la vida del config (evita reconstrucción de árbol al cambiar
     * de tamaño, y evita el costo cuando ningún tamaño usa drawer).
     */
    internal val hasAnyDrawer: Boolean = drawerSheets.isNotEmpty()

    /* -------------------- Access API -------------------- */

    /**
     * TopBar correspondiente al ancho actual.
     */
    fun topBar(size: S): (@Composable () -> Unit)? = topBars[size]

    /**
     * BottomBar correspondiente al ancho actual.
     */
    fun bottomBar(size: S): (@Composable () -> Unit)? = bottomBars[size]

    /**
     * FloatingActionButton correspondiente al ancho actual.
     */
    fun fab(size: S): (@Composable () -> Unit)? = fabs[size]

    /**
     * Posición del FAB, con fallback seguro.
     */
    fun fabPosition(size: S): FabPosition =
        fabPositions[size] ?: FabPosition.End

    /**
     * NavigationRail correspondiente al ancho actual.
     */
    fun navigationRail(size: S): (@Composable () -> Unit)? = navigationRails[size]

    /**
     * Drawer correspondiente al ancho actual.
     */
    fun drawerSheet(size: S): (@Composable (closeDrawer: () -> Unit) -> Unit)? =
        drawerSheets[size]
}