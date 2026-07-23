package org.example.project.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import org.example.project.model.*

@Composable
fun Desktop(folderList: List<Folder>) {
    for (folder in folderList) {
        key(folder.id) {
            FolderCard(folder)
        }
    }
}