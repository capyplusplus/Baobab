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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
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
        .offset(AppState.mousePosition.x.dp, AppState.mousePosition.y.dp)
        .background(UIColors.frame, RoundedCornerShape(8.dp))
        .padding(5.dp, 5.dp)
        .onPointerEvent(PointerEventType.Enter) {
            AppState.onDesktopPopup = true
        }.onPointerEvent(PointerEventType.Exit) {
            AppState.onDesktopPopup = false
        }

    @OptIn(ExperimentalComposeUiApi::class)
    fun folderPopup() = Modifier // Folder popup
        .offset(AppState.mousePosition.x.dp, AppState.mousePosition.y.dp)
        .background(UIColors.frame, RoundedCornerShape(8.dp))
        .onPointerEvent(PointerEventType.Enter) {
            AppState.onFolderPopup = true
        }
        .onPointerEvent(PointerEventType.Exit) {
            AppState.onFolderPopup = false
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
                AppState.deletingFolder?.states?.deleting = false
                AppState.deletingFolder = null
            }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    fun folder(folder: Folder): Modifier {
        val bg:Color =  if (folder.states.deleting) UIColors.deleting
                        else if (folder.states.pressed) UIColors.selected
                        else if (folder.states.hovered) UIColors.hovered
                        else Color.Transparent

        return Modifier.offset((folder.position.x - AppState.camera.x).dp, (folder.position.y - AppState.camera.y).dp)
            .onPointerEvent(PointerEventType.Enter) { folder.hover() }
            .onPointerEvent(PointerEventType.Exit) { folder.unhover() }
            .pointerInput(Unit) { Drags.run { dragFolder(folder) } }
            .background(bg, RoundedCornerShape(8.dp))
            .onPointerEvent(PointerEventType.Press) { event -> Clicks.folderClick(event, folder) }
            .padding(8.dp, 4.dp)
    }
}
