package org.example.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.model.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds
import androidx.compose.ui.text.style.TextAlign
import org.example.project.theme.*

var nextId:Long = 2

@Composable
fun BoxScope.MutedText() {
    Text("Baobab\nv 0.1",
        modifier = Modifier.align(Alignment.BottomStart),
        color = UIColors.muted)
    Text("Made by capyplusplus",
        fontSize = FontSize.huge,
        color = UIColors.muted,
        modifier = Modifier.align(Alignment.BottomCenter)
            .visible(AppState.showLabel))
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.FolderPopup() {
    Column(modifier = Modifiers.folderPopup()) {
        Text("Open", color = UIColors.primary, fontSize = FontSize.big)
        Text("Rename", color = UIColors.primary, fontSize = FontSize.big, modifier = Modifiers.renameFolder)
        HorizontalDivider(thickness = 1.dp, color = Color.White, modifier = Modifier.width(80.dp))
        Text("Delete", color = UIColors.danger, fontSize = FontSize.big, modifier = Modifiers.openDeleteFrame)
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BoxScope.DesktopPopup() {
    Column(modifier = Modifiers.desktopPopup()) {
        Text("New Baobab", color = UIColors.confirm, fontSize = FontSize.big, modifier = Modifiers.newBaobab)
    }
}

@Composable
fun BoxScope.DeleteFrame() {
    Box(modifier = Modifiers.deleteFrame.align(Alignment.Center)) {
        Text("Do you want to delete ${AppState.deletingFolder?.name}?",
            color = UIColors.danger,
            fontSize = FontSize.big,
            modifier = Modifier.align(Alignment.TopCenter).offset(0.dp, (15).dp), textAlign = TextAlign.Center)
        Text("Delete",
            color = UIColors.primary,
            fontSize = FontSize.regular,
            modifier = Modifiers.run{ deleteFolder() })
        Text("Cancel",
            color = UIColors.primary,
            fontSize = FontSize.regular,
            modifier = Modifiers.run{ cancelDelete() })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainDesktop() {
    Box (modifier = Modifiers.mainDesktop) {
        Desktop(AppState.Folders)
        MutedText()

        if (AppState.deletingFolder != null) DeleteFrame()
        if (AppState.folderPopupVisible) FolderPopup()
        if (AppState.desktopPopupVisible) DesktopPopup()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun App() {
    LaunchedEffect(Unit) {
        delay(5000.milliseconds)
        AppState.showLabel = false
    }

    Surface(modifier = Modifier.fillMaxSize(),
            color = UIColors.background) {
        MainDesktop()
    }
}

