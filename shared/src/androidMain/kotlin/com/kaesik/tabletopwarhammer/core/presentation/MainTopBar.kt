package com.kaesik.tabletopwarhammer.core.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar(
    title: String,
    onBackClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    showBackButton: Boolean,
    showSettingsButton: Boolean,
    showHelpButton: Boolean,
    showAboutButton: Boolean,
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            } else null
        },
        actions = {
            if (showHelpButton) {
                IconButton(onClick = onHelpClick) {
                    Icon(Icons.Default.Help, contentDescription = "Help")
                }
            }
            if (showAboutButton) {
                IconButton(onClick = onAboutClick) {
                    Icon(Icons.Default.Info, contentDescription = "About")
                }
            }
            if (showSettingsButton) {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    )
}

@Preview
@Composable
fun MainTopBarPreview() {
    MainTopBar(
        title = "Main Top Bar",
        onBackClick = {},
        onSettingsClick = {},
        onHelpClick = {},
        onAboutClick = {},
        showBackButton = true,
        showSettingsButton = true,
        showHelpButton = true,
        showAboutButton = true
    )
}
