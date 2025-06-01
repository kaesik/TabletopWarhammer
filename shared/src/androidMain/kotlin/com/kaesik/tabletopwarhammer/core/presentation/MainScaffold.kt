package com.kaesik.tabletopwarhammer.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScaffold(
    title: String = "",
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    onBackClick: () -> Unit = {},
    isLoading: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier.fillMaxSize(),
    showBackButton: Boolean = true,
    showSettingsButton: Boolean = true,
    showHelpButton: Boolean = false,
    showAboutButton: Boolean = false
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        topBar = {
            MainTopBar(
                title = title,
                onBackClick = onBackClick,
                showBackButton = showBackButton,
                showSettingsButton = showSettingsButton,
                showHelpButton = showHelpButton,
                showAboutButton = showAboutButton,

            )
        },
        bottomBar = {
            MainBottomBar()
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                isError && errorMessage != null -> Text(
                    text = errorMessage,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )

                else -> content(padding)
            }
        }
    }
}

@Preview
@Composable
fun MainScaffoldPreview() {
    MainScaffold(
        content = {},
        onBackClick = {},
        snackbarHost = {
            SnackbarHost(
                hostState = remember { SnackbarHostState() }
            )
        },
        isLoading = false,
        isError = false,
        errorMessage = null
    )
}
