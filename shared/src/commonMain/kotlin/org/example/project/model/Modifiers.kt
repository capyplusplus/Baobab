package org.example.project.model

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.example.project.model.*
import org.example.project.theme.UIColors

object Modifiers {
    fun justClick(func: () -> Unit): Modifier {
        return Modifier.clickable { func() }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    val mainDesktop = Modifier
        .fillMaxSize()
        .safeContentPadding()
        .onPointerEvent(PointerEventType.Press) { event -> Clicks.mainDesktopClicks(event) }
        .pointerInput(Unit) { Drags.run { dragDesktop() } }
    val deleteFrame = Modifier
        .background(UIColors.frame, shape = RoundedCornerShape(8.dp))
        .border(2.dp, UIColors.danger, shape = RoundedCornerShape(8.dp))
        .size(375.dp, 150.dp)
        .padding(5.dp)

    @OptIn(ExperimentalComposeUiApi::class)
    fun desktopPopup() = Modifier
        .offset(mousePosition.x.dp, mousePosition.y.dp)
        .background(UIColors.frame, RoundedCornerShape(8.dp))
        .padding(5.dp, 5.dp)
        .onPointerEvent(PointerEventType.Enter) {
            onDesktopPopup = true
        }.onPointerEvent(PointerEventType.Exit) {
            onDesktopPopup = false
        }

    @OptIn(ExperimentalComposeUiApi::class)
    fun folderPopup() = Modifier // Folder popup
        .offset(mousePosition.x.dp, mousePosition.y.dp)
        .background(UIColors.frame, RoundedCornerShape(8.dp))
        .onPointerEvent(PointerEventType.Enter) {
            onFolderPopup = true
        }
        .onPointerEvent(PointerEventType.Exit) {
            onFolderPopup = false
        }
        .padding(5.dp, 5.dp)

    val newBaobab = justClick { Clicks.newBaobab() }
    val openDeleteFrame = justClick { Clicks.openDeleteFrame() }
    val renameFolder = justClick { Clicks.renameFolder() }

    @Composable
    fun BoxScope.deleteFolder():Modifier {
        return Modifier.align(Alignment.BottomStart).offset(25.dp, (-15).dp).clickable { Clicks.deleteFolder() }
    }

    @Composable
    fun BoxScope.cancelDelete():Modifier {
        return Modifier.align(Alignment.BottomEnd).offset((-25).dp, (-15).dp)
            .clickable {
                deletingFolder = null
            }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun folder(states: FolderStates, folder: Folder): Modifier {
        val bg:Color = if (pressedFolder == folder) UIColors.selected
                        else if (states.hovered) UIColors.hovered
                        else Color.Transparent

        return Modifier.offset((folder.position.x - camera.x).dp, (folder.position.y - camera.y).dp)
            .onPointerEvent(PointerEventType.Enter) {
                selectedFolder = folder
                states.hovered = true
            }.onPointerEvent(PointerEventType.Exit) {
                selectedFolder = null
                states.hovered = false
            }.pointerInput(Unit) {
                Drags.run { dragFolder(folder, states) }
            }.background(bg, RoundedCornerShape(8.dp))
            .onPointerEvent(PointerEventType.Press) { event ->
                Clicks.folderClick(event, folder, states)
            }.padding(8.dp, 4.dp)
    }
}
