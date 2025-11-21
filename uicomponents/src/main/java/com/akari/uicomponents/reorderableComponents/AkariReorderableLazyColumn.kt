package com.akari.uicomponents.reorderableComponents

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> AkariReorderableLazyColumn(
    items: List<T>,
    state: AkariReorderableState<T>,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    dragActivation: DragActivation = DragActivation.Immediate,
    lazyListState: LazyListState = rememberLazyListState(),
    key: (T) -> Any,
    itemContent: @Composable (item: T, isDragging: Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()

    // Auto-scroll con throttling para evitar llamadas excesivas
    val autoScrollJob = remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(state.draggedIndex) {
        if (state.draggedIndex == null) {
            autoScrollJob.value?.cancel()
            return@LaunchedEffect
        }

        // Continuous auto-scroll check
        while (state.draggedIndex != null) {
            val layoutInfo = lazyListState.layoutInfo
            val draggedIdx = state.draggedIndex ?: break
            val draggedItem = layoutInfo.visibleItemsInfo
                .firstOrNull { it.index == draggedIdx }

            if (draggedItem != null) {
                val itemCenter = draggedItem.offset + state.draggedOffsetY + draggedItem.size / 2f
                val viewportStart = layoutInfo.viewportStartOffset
                val viewportEnd = layoutInfo.viewportEndOffset
                val scrollThreshold = 100f
                val scrollSpeed = 10f

                when {
                    itemCenter < viewportStart + scrollThreshold -> {
                        lazyListState.scrollBy(-scrollSpeed)
                    }
                    itemCenter > viewportEnd - scrollThreshold -> {
                        lazyListState.scrollBy(scrollSpeed)
                    }
                }
            }
            delay(16) // ~60fps
        }
    }

/*    // Auto-scroll cuando se arrastra cerca de los bordes
    LaunchedEffect(state.draggedIndex, state.draggedOffsetY) {
        val draggedIdx = state.draggedIndex ?: return@LaunchedEffect
        val layoutInfo = lazyListState.layoutInfo
        val draggedItem = layoutInfo.visibleItemsInfo
            .firstOrNull { it.index == draggedIdx } ?: return@LaunchedEffect

        val itemCenter = draggedItem.offset + state.draggedOffsetY + draggedItem.size / 2f
        val viewportStart = layoutInfo.viewportStartOffset
        val viewportEnd = layoutInfo.viewportEndOffset
        val scrollThreshold = 100f

        when {
            itemCenter < viewportStart + scrollThreshold -> {
                lazyListState.animateScrollBy(-draggedItem.size.toFloat())
            }
            itemCenter > viewportEnd - scrollThreshold -> {
                lazyListState.animateScrollBy(draggedItem.size.toFloat())
            }
        }
    }*/

    LazyColumn(
        modifier = modifier,
        state = lazyListState
    ) {
        itemsIndexed(
            items = items,
            key = { _, item -> key(item) }
        ) { index, item ->
            val isDragging = state.draggedIndex == index

            AkariReorderableItem(
                index = index,
                state = state,
                lazyListState = lazyListState,
                enabled = enabled,
                dragActivation = dragActivation,
                isDragging = isDragging
            ) {
                itemContent(item, isDragging)
            }
        }
    }
}
