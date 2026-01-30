package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.checkbox.AkariCheckBox

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CheckBoxExample(){
    var checked by remember { mutableStateOf(false) }
    AkariCheckBox(
        modifier = Modifier,
        checked = checked,
        onCheckedChange = { checked = it },
        shape = MaterialTheme.shapes.small,
        iconUnselected = {
            Icon(Icons.Filled.Pets, contentDescription = null)
        },
        iconSelected = {
            Icon(Icons.Filled.Person, contentDescription = null)
        }
    )
}