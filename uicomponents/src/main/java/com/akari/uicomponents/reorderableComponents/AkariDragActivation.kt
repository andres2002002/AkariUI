package com.akari.uicomponents.reorderableComponents

/**
 * Defines the user interaction required to initiate a drag-and-drop operation.
 *
 * @property Immediate The drag operation starts immediately when the user touches the component.
 * @property LongPress The drag operation starts only after the user performs a long-press gesture on the component.
 */
enum class DragActivation {
    Immediate,
    LongPress
}