package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.domain.util.SessionManager
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreenRoot(
    viewModel: AndroidMenuViewModel = koinViewModel(),
    onNavigateToLibraryScreen: () -> Unit,
    onNavigateToCharacterSheetScreen: () -> Unit,
    onNavigateToCharacterCreatorScreen: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    MenuScreen(
        state = state,
        onEvent = { event ->
            coroutineScope.launch {
                when (event) {
                    is MenuEvent.NavigateToLibraryScreen -> onNavigateToLibraryScreen()
                    is MenuEvent.NavigateToCharacterSheetScreen -> onNavigateToCharacterSheetScreen()
                    is MenuEvent.NavigateToCharacterCreatorScreen -> onNavigateToCharacterCreatorScreen()
                    is MenuEvent.OnLogoutClick -> onLogoutClick()
                    else -> Unit
                }

                viewModel.onEvent(event)
            }
        }
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onEvent: (MenuEvent) -> Unit
) {
    MainScaffold(
        title = "Menu",
        showBackButton = false,
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    WarhammerButton(
                        text = "LibraryScreen",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(MenuEvent.NavigateToLibraryScreen) },
                        isLoading = state.isLoading,
                        enabled = !state.isLoading
                    )
                }
                item {
                    WarhammerButton(
                        text = "CharacterSheetScreen",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(MenuEvent.NavigateToCharacterSheetScreen) },
                        isLoading = state.isLoading,
                        enabled = !state.isLoading
                    )
                }
                item {
                    WarhammerButton(
                        text = "CharacterCreatorScreen",
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { onEvent(MenuEvent.NavigateToCharacterCreatorScreen) },
                        isLoading = state.isLoading,
                        enabled = !state.isLoading
                    )
                }

                if (SessionManager.isLoggedIn) {
                    item {
                        WarhammerButton(
                            text = "Logout",
                            onClick = {
                                SessionManager.isLoggedIn = false
                                onEvent(MenuEvent.OnLogoutClick)
                            },
                            isLoading = state.isLoading,
                            enabled = !state.isLoading,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        state = MenuState(),
        onEvent = {},
    )
}
