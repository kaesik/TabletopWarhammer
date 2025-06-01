package com.kaesik.tabletopwarhammer.core.presentation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainBottomBar(
    onHomeClick: () -> Unit = {},
    onLibraryClick: () -> Unit = {},
    onCharacterClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {}
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            IconButton(onClick = onSettingsClick) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }
            IconButton(onClick = onHomeClick) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorite")
            }
            IconButton(onClick = onLibraryClick) {
                Icon(Icons.Default.MenuBook, contentDescription = "Library")
            }
            IconButton(onClick = onCharacterClick) {
                Icon(Icons.Default.Person, contentDescription = "Character")
            }
        }
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    MainBottomBar(
        onHomeClick = {},
        onLibraryClick = {},
        onCharacterClick = {},
        onSettingsClick = {}
    )
}
