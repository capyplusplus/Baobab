package org.example.project.ui

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.visible
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
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

val Folders = listOf<Folder>(
    Folder("Math", 1, Position(30F, 30F)),
    Folder("Eng", 2, Position(200F, 100F))
)

var showLabel by mutableStateOf(true)

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
                .onPointerEvent(PointerEventType.Press) {
                    if (selectedFolder == null) {
                        pressedFolder = null
                    }
                }
                .pointerInput(Unit) {
                detectDragGestures (
                    onDrag = { change, dragAmount ->
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
        }
    }
}

