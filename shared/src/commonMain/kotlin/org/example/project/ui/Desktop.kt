package org.example.project.ui

import androidx.compose.runtime.Composable
import org.example.project.model.*
import org.example.project.ui.*

@Composable
fun Desktop(folderList: List<Folder>) {
    for (folder in folderList) {
        FolderCard(folder)
    }
}