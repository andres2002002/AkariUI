package com.akari.uicomponents.ui.examples

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DragHandle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.akari.uicomponents.reorderableComponents.AkariReorderableColumn
import com.akari.uicomponents.reorderableComponents.AkariReorderableLazyColumn
import com.akari.uicomponents.reorderableComponents.DragActivation
import com.akari.uicomponents.reorderableComponents.rememberAkariReorderableColumnState
import com.akari.uicomponents.reorderableComponents.rememberAkariReorderableLazyState

@Composable
fun DragAndDropExample() {
    val items = remember {
        mutableStateListOf(
            "Akari",
            "Compose",
            "Hilt",
            "Room",
            "Navigation",
            "Python",
            "Jetpack",
            "Reorderable",
            "Lazy",
            "Column",
            "State",
            "List",
            "Reorder",
            "Drag",
            "Drop",
            "Akari_1",
            "Compose_1",
            "Hilt_1",
            "Room_1",
            "Navigation_1",
            "Python_1",
            "Jetpack_1",
            "Reorderable_1",
            "Lazy_1",
            "Column_1",
            "State_1",
            "List_1",
            "Reorder_1",
            "Drag_1",
            "Drop_1"
        )
    }

    val reorderState = rememberAkariReorderableLazyState<String>{ from, to ->
        items.apply { add(to, removeAt(from)) }
    }

    AkariReorderableLazyColumn(
        items = items,
        state = reorderState,
        reorderingEnabled = true,  // false para deshabilitar
        dragActivation = DragActivation.LongPress,
        key = { it }
    ) { item, isDragging ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDragging) Color.Gray.copy(alpha = 0.3f)
                else Color.DarkGray
            )
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.akariDragHandle(),
                    imageVector = Icons.Default.DragHandle, contentDescription = null)
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun DragDropColumnExample() {
    val items = remember {
        mutableStateListOf(
            "Akari",
            "Compose",
            "Hilt",
            "Room",
            "Navigation",
            "Python",
            "Jetpack",
            "Reorderable",
            "Lazy",
            "Column",
            "State",
            "List",
            "Reorder",
            "Drag",
            "Drop",
            "Akari_1",
            "Compose_1",
            "Hilt_1",
            "Room_1",
            "Navigation_1",
            "Python_1",
            "Jetpack_1",
            "Reorderable_1",
            "Lazy_1",
            "Column_1",
            "State_1",
            "List_1",
            "Reorder_1",
            "Drag_1",
            "Drop_1"
        )
    }

    val reorderState = rememberAkariReorderableColumnState<String> { from, to ->
        items.apply { add(to, removeAt(from)) }
    }

    AkariReorderableColumn(
        items = items,
        state = reorderState
    ) { item, isDragging ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isDragging) Color.Gray.copy(alpha = 0.25f)
                else Color.DarkGray
            )
        ) {
            Row(
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.akariDragHandle(),
                    imageVector = Icons.Default.DragHandle, contentDescription = null)
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        }
    }
}
