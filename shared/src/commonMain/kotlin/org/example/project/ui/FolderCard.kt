package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FolderCard(folder:Folder, camera:Camera) {
    var hovered by remember {mutableStateOf(false)}
    var posX by remember { mutableStateOf(folder.position.x) }
    var posY by remember { mutableStateOf(folder.position.y) }

    val bg = if (pressedFolder == folder) Color(0F, 200F, 255F, 0.3F) else Color(0, 0, 0, 1)
    Column(
        modifier = Modifier.offset((posX - camera.x).dp, (posY- camera.y).dp)
            .size(cardSize.dp, cardSize.dp)
            .clickable {
                pressedFolder = folder
                println("${folder.name} should be opened!")
            }.onPointerEvent(PointerEventType.Enter) {
                selectedFolder = folder
                hovered = true
            }.onPointerEvent(PointerEventType.Exit) {
                selectedFolder = null
                hovered = false
            }.pointerInput(Unit) {
                detectDragGestures (
                    onDrag = { change, dragAmount ->
                        if (hovered) {
                            pressedFolder = null
                            folder.position.x += dragAmount.x
                            folder.position.y += dragAmount.y
                            posX = folder.position.x
                            posY = folder.position.y
                        }
                        change.consume()
                    }
                )
            }.background(bg, RoundedCornerShape(8.dp))
    ) {
        Image(
            painter = painterResource(Res.drawable.folder),
            contentDescription = "Folder",
            modifier = Modifier.size(imageSize.dp, imageSize.dp)
                .align(Alignment.CenterHorizontally)
        )
        val folderName = if (hovered) ">${folder.name}<" else folder.name
        Text(folderName, color = Color(255, 255, 255),
            fontSize = fontSize.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}