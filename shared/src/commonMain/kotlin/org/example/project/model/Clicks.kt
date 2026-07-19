package org.example.project.model

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed

object Clicks {
    fun newBaobab() {
        AppState.Folders.add(AppState.Folders.size, Folder(
            "Unnamed ${AppState.Folders.size}", AppState.nextId.toInt(),
            Position(AppState.mousePosition.x + AppState.camera.x, AppState.mousePosition.y + AppState.camera.y)))
        AppState.desktopPopupVisible = false
        AppState.nextId++
    }

    fun openDeleteFrame() {
        AppState.pressedFolder?.deleteQuery()
        AppState.folderPopupVisible = false
    }

    fun renameFolder() {
        AppState.folderPopupVisible = false
        AppState.pressedFolder?.rename()
    }

    fun deleteFolder() {
        AppState.Folders.remove(AppState.deletingFolder)
        AppState.deletingFolder = null
    }

    fun mainDesktopClicks(event: PointerEvent) {
        when {
            event.buttons.isPrimaryPressed -> {
                if (AppState.deletingFolder == null) {
                    if (!AppState.onFolderPopup) AppState.folderPopupVisible = false
                    if (!AppState.onDesktopPopup) AppState.desktopPopupVisible = false
                    if (AppState.hoveredFolder == null && !AppState.folderPopupVisible) {
                        if (AppState.pressedFolder != null) AppState.pressedFolder?.unpress()

                        AppState.renamingFolder?.remameEnd()
                    }
                }
            }
            event.buttons.isSecondaryPressed -> {
                AppState.mousePosition = event.changes.first().position
                if (AppState.deletingFolder == null && AppState.hoveredFolder == null) {
                    AppState.folderPopupVisible = false
                    if (AppState.pressedFolder != null) AppState.pressedFolder?.unpress()

                    AppState.renamingFolder?.remameEnd()

                    AppState.desktopPopupVisible = true
                }
            }
        }
    }

    fun folderClick(event: PointerEvent, folder:Folder) {
        when {
            event.buttons.isSecondaryPressed -> {
                if (folder.states.hovered && AppState.deletingFolder == null) {
                    folder.press()

                    AppState.folderPopupPosition = Position(folder.position.x, folder.position.y)
                    AppState.folderPopupVisible = true
                    AppState.desktopPopupVisible = false
                }
            }
            event.buttons.isPrimaryPressed -> {
                if (AppState.deletingFolder == null) folder.press()
            }
        }
    }

}

object Drags {
    suspend fun PointerInputScope.dragDesktop() {
        detectDragGestures (
            onDrag = { change, dragAmount ->
                AppState.desktopPopupVisible = false
                if (AppState.hoveredFolder == null) {
                    AppState.camera = AppState.camera.copy(
                        x = AppState.camera.x - dragAmount.x,
                        y = AppState.camera.y - dragAmount.y
                    )
                }
                change.consume()
            }
        )
    }
    suspend fun PointerInputScope.dragFolder(folder:Folder) {
        detectDragGestures (
            onDrag = { change, dragAmount ->
                if (folder.states.hovered || AppState.draggedFolder == folder) {
                    AppState.folderPopupVisible = false
                    AppState.draggedFolder = folder

                    folder.press()

                    val newX = folder.position.x + dragAmount.x
                    val newY = folder.position.y + dragAmount.y
                    folder.position = Position(newX, newY)
                }
                change.consume()
            },
            onDragEnd = {
                if (AppState.draggedFolder == folder) AppState.draggedFolder = null
            },
            onDragCancel = {
                if (AppState.draggedFolder == folder) AppState.draggedFolder = null
            }
        )
    }
}