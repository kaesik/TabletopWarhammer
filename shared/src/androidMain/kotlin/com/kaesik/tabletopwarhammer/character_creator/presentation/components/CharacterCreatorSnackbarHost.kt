package com.kaesik.tabletopwarhammer.character_creator.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable

enum class SnackbarType {
    Success,
    Error
}

suspend fun SnackbarHostState.showCharacterCreatorSnackbar(message: String, type: SnackbarType) {
    this.showSnackbar(
        message = message,
        withDismissAction = false,
        duration = SnackbarDuration.Short,
        actionLabel = when (type) {
            SnackbarType.Success -> "success"
            SnackbarType.Error -> "error"
        }
    )
}

@Composable
fun CharacterCreatorSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(snackbarHostState) { snackbarData: SnackbarData ->
        Snackbar(
            snackbarData = snackbarData,
            containerColor = when (snackbarData.visuals.actionLabel) {
                "error" -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.primary
            }
        )
    }
}
