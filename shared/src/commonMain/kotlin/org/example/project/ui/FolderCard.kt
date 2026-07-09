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
import org.example.project.model.*

const val cardSize = 80F
const val imageSize = 50F
const val fontSize = 20

@Composable
fun FolderCard(folder:Folder, camera:Camera) {
    Column(
        modifier = Modifier.offset((folder.position.x - camera.x).dp, (folder.position.y - camera.y).dp)
            .size(cardSize.dp, cardSize.dp)
            .clickable {
                println("${folder.name} should be opened!")
            }
    ) {
        Image(
            painter = painterResource(Res.drawable.folder),
            contentDescription = "Folder",
            modifier = Modifier.size(imageSize.dp, imageSize.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(folder.name, color = Color(255, 255, 255),
            fontSize = fontSize.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally))
    }
}