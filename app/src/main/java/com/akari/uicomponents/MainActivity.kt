package com.akari.uicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.akari.uicomponents.ui.examples.CheckBoxExample
import com.akari.uicomponents.ui.examples.DragAndDropExample
import com.akari.uicomponents.ui.examples.DragDropColumnExample
import com.akari.uicomponents.ui.examples.TextFieldExample
import com.akari.uicomponents.ui.examples.TooltipButtonExample
import com.akari.uicomponents.ui.theme.AkariUITheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AkariUITheme {
                Scaffold() { padding ->
                    Column(modifier = Modifier.padding(padding)) {
                        TooltipButtonExample()
                        CheckBoxExample()
                        TextFieldExample()
                        Text("Reorderable Column")
                        //DragDropColumnExample()
                        DragAndDropExample()
                    }
                }
            }
        }
    }
}