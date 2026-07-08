package org.example.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.model.*

val Folders = listOf<Folder>(
    Folder("Math", 1, Position(30F, 30F)),
    Folder("Eng", 2, Position(200F, 100F))
)

@Composable
@Preview
fun App() {
    Surface(modifier = Modifier.fillMaxSize(),
            color = Color(30, 30, 30)) {
        Box (
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        ) {
            Desktop(Folders)
            Text("Baobab\nv 0.1",
                modifier = Modifier.align(Alignment.BottomStart),
                color = Color(200, 200, 200))
        }
    }
}