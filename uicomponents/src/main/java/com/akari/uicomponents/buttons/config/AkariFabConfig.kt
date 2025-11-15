package com.akari.uicomponents.buttons.config

import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

data class AkariFabConfig(
    val shape: Shape? = null,
    val containerColor: Color? = null,
    val contentColor: Color? = null,
    val elevation: FloatingActionButtonElevation? = null
)