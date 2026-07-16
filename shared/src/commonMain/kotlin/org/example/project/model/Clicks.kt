package org.example.project.model

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import org.example.project.ui.nextId

object Clicks {
    fun newBaobab() {
        Folders.add(Folders.size, Folder(
            "Unnamed ${Folders.size}", nextId.toInt(),
            Position(mousePosition.x + camera.x, mousePosition.y + camera.y)))
        desktopPopupVisible = false
        nextId++
    }

    fun openDeleteFrame() {
        deletingFolder = pressedFolder
        folderPopupVisible = false
    }

    fun renameFolder() {
        folderPopupVisible = false
        renamingFolder = pressedFolder
    }

    fun deleteFolder() {
        Folders.remove(deletingFolder)
        deletingFolder = null
    }

    fun mainDesktopClicks(event: PointerEvent) {
        when {
            event.buttons.isPrimaryPressed -> {
                if (deletingFolder == null) {
                    if (!onFolderPopup) folderPopupVisible = false
                    if (!onDesktopPopup) desktopPopupVisible = false
                    if (selectedFolder == null && !folderPopupVisible) {
                        pressedFolder = null
                        renamingFolder = null
                    }
                }
            }
            event.buttons.isSecondaryPressed -> {
                mousePosition = event.changes.first().position
                if (deletingFolder == null && selectedFolder == null) {
                    folderPopupVisible = false
                    pressedFolder = null
                    renamingFolder = null

                    desktopPopupVisible = true
                }
            }
        }
    }

    fun folderClick(event: PointerEvent, folder:Folder, states: FolderStates) {
        when {
            event.buttons.isSecondaryPressed -> {
                if (selectedFolder == folder && deletingFolder == null) {
                    pressedFolder = folder
                    folderPopupVisible = true
                    desktopPopupVisible = false
                    folderPopupPosition = Position(folder.position.x, folder.position.y)
                }
            }
            event.buttons.isPrimaryPressed -> {
                if (deletingFolder == null) pressedFolder = folder
            }
        }
    }

}

object Drags {
    suspend fun PointerInputScope.dragDesktop() {
        detectDragGestures (
            onDrag = { change, dragAmount ->
                desktopPopupVisible = false
                if (selectedFolder == null) {
                    camera = camera.copy(
                        x = camera.x - dragAmount.x,
                        y = camera.y - dragAmount.y
                    )
                }
                change.consume()
            }
        )
    }
    suspend fun PointerInputScope.dragFolder(folder:Folder, states: FolderStates) {
        detectDragGestures (
            onDrag = { change, dragAmount ->
                if (states.hovered || draggedFolder == folder) {
                    folderPopupVisible = false
                    draggedFolder = folder

                    val newX = folder.position.x + dragAmount.x
                    val newY = folder.position.y + dragAmount.y
                    folder.position = Position(newX, newY)
                }
                change.consume()
            },
            onDragEnd = {
                if (draggedFolder == folder) draggedFolder = null
            },
            onDragCancel = {
                if (draggedFolder == folder) draggedFolder = null
            }
        )
    }
}