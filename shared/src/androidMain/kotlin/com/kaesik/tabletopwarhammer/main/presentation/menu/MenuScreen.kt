package com.kaesik.tabletopwarhammer.main.presentation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerSectionTitle
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerButton
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerGlowingCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreenRoot(
    viewModel: AndroidMenuViewModel = koinViewModel(),
    onNavigateToLibraryScreen: () -> Unit,
    onNavigateToCharacterSheetScreen: () -> Unit,
    onNavigateToCharacterCreatorScreen: () -> Unit,
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
        title = "",
        showBackButton = false,
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                WarhammerSectionTitle("diceHammer")
                Spacer(modifier = Modifier.height(32.dp))
                WarhammerGlowingCard(
                    isLoading = state.isLoading,
                    content = {
                        WarhammerSectionTitle("Character")
                        WarhammerButton(
                            text = "Character Creator",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(MenuEvent.NavigateToCharacterCreatorScreen) },
                            isLoading = state.isLoading,
                            enabled = !state.isLoading
                        )
                        WarhammerButton(
                            text = "Character Sheet",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(MenuEvent.NavigateToCharacterSheetScreen) },
                            isLoading = state.isLoading,
                            enabled = !state.isLoading
                        )
                    },
                )
                Spacer(modifier = Modifier.height(32.dp))
                WarhammerGlowingCard(
                    isLoading = state.isLoading,
                    content = {
                        WarhammerSectionTitle("Library")
                        WarhammerButton(
                            text = "Library",
                            modifier = Modifier.fillMaxWidth(),
                            onClick = { onEvent(MenuEvent.NavigateToLibraryScreen) },
                            isLoading = state.isLoading,
                            enabled = !state.isLoading
                        )
                    },
                )
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
