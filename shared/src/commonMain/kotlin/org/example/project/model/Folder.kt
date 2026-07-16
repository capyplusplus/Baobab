package org.example.project.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class Folder(name:String, id:Int, position: Position) {
    var name by mutableStateOf(name)
    var id by mutableStateOf(id)
    var position by mutableStateOf(position)
    var states = FolderStates(this)
}

