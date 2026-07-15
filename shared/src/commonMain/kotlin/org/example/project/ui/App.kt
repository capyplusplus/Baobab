package org.example.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.model.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.isSecondaryPressed
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.style.TextAlign

var nextId:Long = 2
val Folders = mutableStateListOf(
    Folder("Math", 0, Position(30F, 30F)),
    Folder("Eng", 1, Position(200F, 100F))
)

var showLabel by mutableStateOf(true)
var mousePosition by mutableStateOf(Offset(0F, 0F))

var aboutToDeleteFolder:Folder? by mutableStateOf(null)

var onDesktopPopup = false
var onPopup = false
var renamingFolder: Folder? by mutableStateOf(null)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App() {
    var camera by remember {
        mutableStateOf(Camera(0f, 0f))
    }

    LaunchedEffect(Unit) {
        delay(5000.milliseconds)
        showLabel = false
    }

    Surface(modifier = Modifier.fillMaxSize(),
            color = Color(30, 30, 30)) {
        Box (
            modifier = Modifier

                .fillMaxSize()
                .safeContentPadding()
                .onPointerEvent(PointerEventType.Press) { event ->
                    when {
                        event.buttons.isPrimaryPressed -> {
                            if (aboutToDeleteFolder == null) {
                                if (!onPopup) popupVisible = false
                                if (!onDesktopPopup) desktopPopupVisible = false
                                if (selectedFolder == null && !popupVisible) {
                                    pressedFolder = null
                                    renamingFolder = null
                                }
                            }
                        }
                        event.buttons.isSecondaryPressed -> {
                            mousePosition = event.changes.first().position
                            if (aboutToDeleteFolder == null) {
                                if (selectedFolder == null) {
                                    popupVisible = false
                                    pressedFolder = null
                                    desktopPopupVisible = true
                                    renamingFolder = null
                                }
                            }
                        }
                    }
                }
                .pointerInput(Unit) {
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
        ) {
            Desktop(Folders, camera = camera)
            Text("Baobab\nv 0.1",
                modifier = Modifier.align(Alignment.BottomStart),
                color = Color(200, 200, 200))
            Text("Made by capyplusplus",
                fontSize = 30.sp,
                color = Color(200, 200, 200),
                modifier = Modifier.align(Alignment.BottomCenter)
                    .visible(showLabel))

            Box(modifier = Modifier
                .visible(aboutToDeleteFolder != null)
                .align(Alignment.Center)
                .background(Color(0.05F, 0.05F, 0.05F, 1F), shape = RoundedCornerShape(8.dp))
                .border(2.dp, Color(255, 120, 120), shape = RoundedCornerShape(8.dp))
                .size(375.dp, 150.dp)
                .padding(5.dp)
            ) {
                Text("Do you want to delete ${aboutToDeleteFolder?.name}?",
                    color = Color(255, 120, 120),
                    fontSize = 23.sp,
                    modifier = Modifier.align(Alignment.TopCenter).offset(0.dp, (15).dp), textAlign = TextAlign.Center)
                Text("Delete",
                    color = Color(255, 255, 255),
                    fontSize = 17.sp,
                    modifier = Modifier.align(Alignment.BottomStart).offset(25.dp,(-15).dp)
                        .clickable{
                            Folders.remove(aboutToDeleteFolder)
                            aboutToDeleteFolder = null
                        })
                Text("Cancel",
                    color = Color(255, 255, 255),
                    fontSize = 17.sp,
                    modifier = Modifier.align(Alignment.BottomEnd).offset((-25).dp, (-15).dp)
                        .clickable{
                            aboutToDeleteFolder = null
                        })
            }

            Column(modifier = Modifier // Folder popup
                .visible(popupVisible)
                .offset(mousePosition.x.dp, mousePosition.y.dp)
                .background(Color(60, 60, 60), RoundedCornerShape(8.dp))
                .onPointerEvent(PointerEventType.Enter) {
                    onPopup = true
                }
                .onPointerEvent(PointerEventType.Exit) {
                    onPopup = false
                }
                .padding(5.dp, 5.dp)) {
                Text("Open", color = Color(220, 230, 220), fontSize = 20.sp)
                Text("Rename", color = Color(220, 220, 220), fontSize = 20.sp,
                    modifier = Modifier.clickable{
                        popupVisible = false
                        renamingFolder = pressedFolder
                        println("Renaming: ${renamingFolder?.name}")
                    })
                HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.width(80.dp))
                Text("Delete", color = Color(255, 20, 20), fontSize = 20.sp,
                    modifier = Modifier.clickable{
                        aboutToDeleteFolder = pressedFolder
                        popupVisible = false
                        println("Deleting: ${aboutToDeleteFolder?.name}")
                    })
            }
            Column(modifier = Modifier // Desktop popup
                .visible(desktopPopupVisible)
                .offset(mousePosition.x.dp, mousePosition.y.dp)
                .background(Color(60, 60, 60), RoundedCornerShape(8.dp))
                .padding(5.dp, 5.dp)
                .onPointerEvent(PointerEventType.Enter) {
                    onDesktopPopup = true
                }.onPointerEvent(PointerEventType.Exit) {
                    onDesktopPopup = false
                }) {
                Text("New Baobab", color = Color(10, 230, 15), fontSize = 20.sp,
                    modifier = Modifier.clickable{
                        Folders.add(Folders.size, Folder(
                            "Unnamed ${Folders.size}", nextId.toInt(),
                            Position(mousePosition.x + camera.x, mousePosition.y + camera.y)))
                        desktopPopupVisible = false
                        nextId++
                    })
            }
        }
    }
}

