package com.akari.uicomponents.scaffold

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@Composable
fun<S> AkariScaffold(
    currentSize: S,
    config: AkariScaffoldConfig<S>,
    content: @Composable () -> Unit
) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val currentDrawerContent = config.drawerSheet(currentSize)

    val scaffoldContent: @Composable () -> Unit = {
        Scaffold(
            topBar = {
                config.topBar(currentSize)?.invoke()
            },
            bottomBar = {
                config.bottomBar(currentSize)?.invoke()
            },
            floatingActionButton = {
                config.fab(currentSize)?.invoke()
            },
            floatingActionButtonPosition =
                config.fabPosition(currentSize),
            snackbarHost = { config.snackbarHost?.invoke() },
            containerColor = config.containerColor,
            contentColor = config.contentColor
        ) { padding ->
            Row(Modifier.padding(padding)) {
                config.navigationRail(currentSize)?.invoke()
                Box(Modifier.weight(1f)) {
                    content()
                }
            }
        }
    }

    if (currentDrawerContent != null) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = config.gesturesEnabled,
            drawerContent = {
                currentDrawerContent.invoke {
                    scope.launch { drawerState.close() }
                }
            }
        ) {
            scaffoldContent()
        }
    } else {
        scaffoldContent()
    }
}