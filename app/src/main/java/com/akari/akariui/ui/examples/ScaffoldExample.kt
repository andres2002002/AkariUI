package com.akari.akariui.ui.examples

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.akari.uicomponents.scaffold.AkariScaffold
import com.akari.uicomponents.scaffold.rememberAkariScaffoldConfig
import com.akari.uicomponents.tooltip.AkariTooltip

@Composable
fun ScaffoldExample(
    widthSizeClass: WindowWidthSizeClass
){
    val config = rememberAkariScaffoldConfig {

        // TopBar visible en todos los tamaños
        topBar {
            anyOf(
                WindowWidthSizeClass.Compact,
                WindowWidthSizeClass.Medium,
                WindowWidthSizeClass.Expanded,
                value = { MyCustomTopBar() }
            )
        }

        // BottomBar solo en teléfonos
        bottomBar {
            WindowWidthSizeClass.Compact provides { MyCustomBottomBar() }
        }

        // NavigationRail en tablets y web/desktop
        navigationRail {
            anyOf(
                WindowWidthSizeClass.Medium,
                WindowWidthSizeClass.Expanded,
                value = { MyCustomNavigationRail() }
            )
        }

        // Drawer únicamente para pantallas pequeñas
        drawerSheet {
            WindowWidthSizeClass.Compact provides { closeDrawer ->
                MyCustomDrawerSheet(onClose = closeDrawer)
            }
        }

        // FAB exclusivo para Compact y Medium
        fab {
            anyOf(
                WindowWidthSizeClass.Compact,
                WindowWidthSizeClass.Medium,
                value = { MyCustomFab() }
            )
        }
    }


    Surface() {
        AkariScaffold(
            currentSize = widthSizeClass,
            config = config
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                TooltipButtonExample()
                CheckBoxExample()
                TextFieldExample()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyCustomTopBar() {
    TopAppBar(
        title = { Text("Custom TopBar") }
    )
}

@Composable
private fun MyCustomBottomBar() {
    BottomAppBar() {
        Text("Custom BottomBar")
    }
}

@Composable
private fun MyCustomNavigationRail() {
    NavigationRail() {
        Text("Custom NavigationRail")
    }
}

@Composable
private fun MyCustomDrawerSheet(onClose: () -> Unit) {
    ModalDrawerSheet() {
        Text("Custom Drawer")
        Button(onClick = onClose) {
            Text("Close Drawer")
        }
    }
}

@Composable
private fun MyCustomFab() {
    AkariTooltip(
        tooltipContent = {
            Text(text = "FAB Example")
        }
    ) {
        FloatingActionButton(
            onClick = {  }
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}