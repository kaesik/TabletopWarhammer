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
import com.kaesik.tabletopwarhammer.core.domain.Route
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import com.kaesik.tabletopwarhammer.core.domain.util.SessionManager
import com.kaesik.tabletopwarhammer.core.presentation.di.LocalNavController

@Composable
fun MainScaffold(
    title: String = "",
    snackbarHost: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
    isLoading: Boolean,
    isError: Boolean,
    error: DataError?,
    modifier: Modifier = Modifier.fillMaxSize(),
    showBackButton: Boolean = true,
    showMenuButton: Boolean = true,
) {
    val navController = LocalNavController.current
    Scaffold(
        modifier = modifier,
        snackbarHost = snackbarHost,
        topBar = {
            MainTopBar(
                title = title,
                onBackClick = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    } else {
                        navController.navigate(Route.Intro) {
                            popUpTo(Route.Intro) { inclusive = true }
                        }
                    }
                },
                onSettingsClick = {
                    navController.navigate(Route.Settings)
                },
                onHelpClick = {

                },
                onAboutClick = {
                    navController.navigate(Route.About)
                },
                onLoginClick = {
                    navController.navigate(Route.Login)
                },
                onLogoutClick = {
                    navController.navigate(Route.Intro) {
                        popUpTo(Route.Intro) { inclusive = true }
                    }
                    SessionManager.isLoggedIn = false
                },
                showBackButton = showBackButton,
                showMenuButton = showMenuButton,
            )
        },
        bottomBar = {
            MainBottomBar(
                onHomeClick = {
                    navController.navigate(Route.Menu) {
                        popUpTo(Route.Menu) { inclusive = true }
                    }
                },
                onFavoriteClick = {

                },
                onLibraryClick = {
                    navController.navigate(Route.Library)
                },
                onUserClick = {

                },
            )
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
                    text = DataException(error).message,
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.error
                )

                else -> Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    content(padding)
                }
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
