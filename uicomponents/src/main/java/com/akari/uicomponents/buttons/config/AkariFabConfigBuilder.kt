package com.akari.uicomponents.buttons.config

import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

class AkariFabConfigBuilder {
    var shape: Shape? = null
    var containerColor: Color? = null
    var contentColor: Color? = null
    var elevation: FloatingActionButtonElevation? = null

    internal fun build(): AkariFabConfig =
        AkariFabConfig(
            shape = shape,
            containerColor = containerColor,
            contentColor = contentColor,
            elevation = elevation,
        )
}

fun AkariFabConfig(
    builder: AkariFabConfigBuilder.() -> Unit
): AkariFabConfig = AkariFabConfigBuilder().apply(builder).build()