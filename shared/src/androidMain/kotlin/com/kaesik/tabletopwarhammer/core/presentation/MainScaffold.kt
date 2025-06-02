package com.kaesik.tabletopwarhammer.core.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException

@Composable
fun MainScaffold(
    title: String = "",
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    onBackClick: () -> Unit = {},
    isLoading: Boolean,
    isError: Boolean,
    error: DataError?,
    modifier: Modifier = Modifier.fillMaxSize(),
    showBackButton: Boolean = true,
    showMenuButton: Boolean = true,
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        topBar = {
            MainTopBar(
                title = title,
                onBackClick = onBackClick,
                showBackButton = showBackButton,
                showMenuButton = showMenuButton,
            )
        },
        bottomBar = {
            MainBottomBar()
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when {
                isLoading -> CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.primary,
                )

                isError && error != null -> Text(
                    text = DataException(error).message ?: "Unknown error",
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
        title = "Preview Scaffold",
        content = {},
        onBackClick = {},
        snackbarHost = {
            SnackbarHost(
                hostState = remember { SnackbarHostState() }
            )
        },
        isLoading = false,
        isError = true,
        error = DataError.Network.NO_INTERNET
    )
}
