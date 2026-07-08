package org.example.project.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import baobab.shared.generated.resources.Res
import baobab.shared.generated.resources.folder
import org.jetbrains.compose.resources.painterResource

data class Folder(var name:String, var id:Int, var position: Position);

