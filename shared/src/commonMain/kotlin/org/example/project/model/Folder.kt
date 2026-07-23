package org.example.project.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Folder(name:String, id:Int, position: Position) {
    var name by mutableStateOf(name)
    var id by mutableStateOf(id)
    var position by mutableStateOf(position)
    var states = FolderState()

    fun hover() {
        this.states.hovered = true
        AppState.hoveredFolder = this
    }
    fun unhover() {
        this.states.hovered = false
        if (AppState.hoveredFolder == this) AppState.hoveredFolder = null
    }
    fun press() {
        if (AppState.pressedFolder != null && AppState.pressedFolder != this) {
            AppState.pressedFolder?.unpress()
        }
        this.states.pressed = true
        AppState.pressedFolder = this
    }
    fun unpress() {
        this.states.pressed = false
        AppState.pressedFolder = null
    }

    fun rename() {
        AppState.renamingFolder = this
        this.states.renamed = true
    }

    fun deleteQuery() {
        this.states.deleting = true
        AppState.deletingFolder = this
    }

    fun renameEnd() {
        AppState.renamingFolder = null
        this.states.renamed = false
    }
}
