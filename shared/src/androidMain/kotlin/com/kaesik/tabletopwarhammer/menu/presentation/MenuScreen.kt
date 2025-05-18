package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.core.presentation.Button1
import org.koin.androidx.compose.koinViewModel

@Composable
fun MenuScreenRoot(
    viewModel: AndroidMenuViewModel = koinViewModel(),
    onNavigateToLibraryScreen: () -> Unit,
    onNavigateToCharacterSheetScreen: () -> Unit,
    onNavigateToCharacterCreatorScreen: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MenuScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is MenuEvent.NavigateToLibraryScreen -> onNavigateToLibraryScreen()
                is MenuEvent.NavigateToCharacterSheetScreen -> onNavigateToCharacterSheetScreen()
                is MenuEvent.NavigateToCharacterCreatorScreen -> onNavigateToCharacterCreatorScreen()
                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    onEvent: (MenuEvent) -> Unit
) {
    Scaffold(

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text("Menu Screen")
                    Button1(
                        text = "LibraryScreen",
                        onClick = { onEvent(MenuEvent.NavigateToLibraryScreen) }
                    )
                    Button1(
                        text = "CharacterSheetScreen",
                        onClick = { onEvent(MenuEvent.NavigateToCharacterSheetScreen) }
                    )
                    Button1(
                        text = "CharacterCreatorScreen",
                        onClick = { onEvent(MenuEvent.NavigateToCharacterCreatorScreen) }
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(
        state = MenuState(),
        onEvent = {}
    )
}
