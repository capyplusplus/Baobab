package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baobab.shared.generated.resources.Res
import baobab.shared.generated.resources.folder
import org.example.project.model.Folder
import org.jetbrains.compose.resources.painterResource
import org.example.project.model.*

const val cardSize = 80F
const val imageSize = 50F
const val fontSize = 20

var selectedFolder:Folder? = null
var pressedFolder:Folder? by mutableStateOf(null)

var popupVisible by mutableStateOf(false)
var popupPosition: Position by mutableStateOf(Position(0F, 0F))
var desktopPopupVisible by mutableStateOf(false)
var draggedFolder:Folder? = null

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FolderCard(folder:Folder, camera:Camera) {
    var hovered by remember {mutableStateOf(false)}
    var posX by remember { mutableStateOf(folder.position.x) }
    var posY by remember { mutableStateOf(folder.position.y) }
    var text by remember { mutableStateOf(folder.name) }

    val bg:Color
    if (pressedFolder == folder) {
        bg = Color(0F, 0.5F, 1F, 0.3F)
    } else if (hovered) {
        bg = Color(0.5F, 0.5F, 0.5F, 0.2F)
    } else {
        bg = Color(0, 0, 0, 1)
    }

    Column(
        modifier = Modifier.offset((posX - camera.x).dp, (posY- camera.y).dp)
            .onPointerEvent(PointerEventType.Enter) {
                selectedFolder = folder
                hovered = true
            }.onPointerEvent(PointerEventType.Exit) {
                selectedFolder = null
                hovered = false
            }.pointerInput(Unit) {
                detectDragGestures (
                    onDrag = { change, dragAmount ->
                        if (hovered || draggedFolder == folder) {
                            popupVisible = false
                            draggedFolder = folder
                            folder.position.x += dragAmount.x
                            folder.position.y += dragAmount.y
                            posX = folder.position.x
                            posY = folder.position.y
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
            }.background(bg, RoundedCornerShape(8.dp))
            .onPointerEvent(PointerEventType.Press) { event ->
                when {
                    event.buttons.isSecondaryPressed -> {
                        if (selectedFolder == folder) {
                            pressedFolder = folder
                            popupVisible = true
                            desktopPopupVisible = false
                            popupPosition = Position(posX, posY)
                        }
                    }
                    event.buttons.isPrimaryPressed -> {
                        pressedFolder = folder
                    }
                }
        }
    ) {
        Image(
            painter = painterResource(Res.drawable.folder),
            contentDescription = "Folder",
            modifier = Modifier.size(imageSize.dp, imageSize.dp)
                .align(Alignment.CenterHorizontally)
        )
        val folderName = folder.name
        if (renamingFolder == folder) {
            BasicTextField(value = text, textStyle = TextStyle(color = Color(200, 200, 200),
                textAlign = TextAlign.Center, fontSize = fontSize.sp), enabled = (renamingFolder == folder),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onValueChange = {
                    text = it
                    folder.name = it
                })
        } else {
            Text(folderName, color = Color(255, 255, 255),
                fontSize = fontSize.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}