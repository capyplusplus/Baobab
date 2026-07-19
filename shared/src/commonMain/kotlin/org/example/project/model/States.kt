package org.example.project.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset

object AppState {
    var mousePosition by mutableStateOf(Offset(0F, 0F)) // Position of the mouse (changes when user clicks RMB)
    var camera by mutableStateOf(Camera(0f, 0f))  // main camera

    var hoveredFolder:Folder? = null // folder hovered by user's mouse
    var pressedFolder:Folder? by mutableStateOf(null) // folder which was clicked by user's LMB
    var deletingFolder:Folder? by mutableStateOf(null) // folder, which user wants to delete, and clicked "delete" on it
    var renamingFolder: Folder? by mutableStateOf(null) // folder, which is about to be renamed
    var draggedFolder:Folder? = null // folder which is dragged

    var folderPopupVisible by mutableStateOf(false) // should folder popup be on the screen
    var folderPopupPosition: Position by mutableStateOf(Position(0F, 0F)) // stores where should folder popup be
    var desktopPopupVisible by mutableStateOf(false) // should desktop popup be on the screen

    var showLabel by mutableStateOf(true) // Shows "made by capyplusplus" for 5 seconds when app starts

    var onDesktopPopup = false // var tells whether mouse if on desktop popup (RMB click on desktop) or not
    var onFolderPopup = false // var tells whether mouse is on folder popup (RMB click on folder) or not

    // main baobabs list
    val Folders: MutableList<Folder> = mutableStateListOf(
    )

    var nextId:Long = 2
}

class FolderState(folder: Folder) {
    var hovered by mutableStateOf(false)
    var renamed by mutableStateOf(false)
    var pressed by mutableStateOf(false)
    var deleting by mutableStateOf(false)
    var dragging = false
}

