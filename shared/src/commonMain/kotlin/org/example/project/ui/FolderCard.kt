package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import baobab.shared.generated.resources.Res
import baobab.shared.generated.resources.folder
import org.example.project.model.Folder
import org.jetbrains.compose.resources.painterResource
import org.example.project.model.*
import org.example.project.theme.FontSize
import org.example.project.theme.ObjectSize
import org.example.project.theme.UIColors

@Composable
fun ColumnScope.FolderIcon() {
    Image(
        painter = painterResource(Res.drawable.folder),
        contentDescription = "Folder",
        modifier = Modifier.size(ObjectSize.FOLDERICON.dp)
            .align(Alignment.CenterHorizontally)
    )
}

@Composable
fun ColumnScope.FolderName(folder: Folder) {
    Text(folder.name, color = UIColors.primary,
        fontSize = FontSize.big,
        textAlign = TextAlign.Center,
        modifier = Modifier.align(Alignment.CenterHorizontally))
}

@Composable
fun ColumnScope.FolderRename(folder: Folder) {
    BasicTextField(value = folder.name, textStyle = TextStyle(color = UIColors.muted,
        textAlign = TextAlign.Center, fontSize = FontSize.big),
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onValueChange = {
            folder.name = it
        })
}

@Composable
fun FolderCard(folder:Folder) {
    Column(modifier = Modifiers.folder(folder)) {
        FolderIcon()

        if (folder.states.renamed) FolderRename(folder)
        else FolderName(folder)
    }
}