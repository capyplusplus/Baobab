package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.model.JsonSystem
import org.example.project.ui.App

fun main() = application {
    JsonSystem.loadFolders()
    JsonSystem.loadCamera()
    Window(
        onCloseRequest = {
            JsonSystem.save()
            exitApplication()
        },
        title = "Baobab",
    ) {
        App()
    }
}