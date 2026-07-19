package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.application
import org.example.project.model.JsonSystem
import org.example.project.ui.App

fun main() = application {
    JsonSystem.load("Folders")
    Window(
        onCloseRequest = {
            JsonSystem.save("Folders")
            exitApplication()
        },
        title = "Baobab",
    ) {
        App()
    }
}