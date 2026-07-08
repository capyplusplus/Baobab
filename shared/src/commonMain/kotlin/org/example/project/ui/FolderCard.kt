package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import baobab.shared.generated.resources.Res
import baobab.shared.generated.resources.folder
import org.example.project.model.Folder
import org.jetbrains.compose.resources.painterResource

@Composable
fun FolderCard(folder:Folder) {
    Column(
        modifier = Modifier.clickable {
            println("${folder.name} should be opened!")
        }.offset(folder.position.x.dp, folder.position.y.dp).size(50.dp, 50.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.folder),
            contentDescription = "Folder",
            modifier = Modifier.size(30.dp, 30.dp)
        )
        Text(folder.name, color = Color(255, 255, 255),
            fontSize = 12.sp)
    }
}