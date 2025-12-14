package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.core.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.core.presentation.components.showWarhammerSnackbar
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterCreatorScreenRoot(
    viewModel: AndroidCharacterCreatorViewModel = koinViewModel(),
    onCreateCharacterSelect: () -> Unit,
    onRandomCharacterSelect: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle messages from the viewModel
    LaunchedEffect(state.message, state.isError) {
        state.message?.let { message ->
            snackbarHostState.showWarhammerSnackbar(
                message = message,
                type = if (state.isError) SnackbarType.Error else SnackbarType.Success
            )
            viewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    CharacterCreatorScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterCreatorEvent.OnCreateCharacterSelect -> {
                    onCreateCharacterSelect()
                }

                is CharacterCreatorEvent.OnRandomCharacterSelect -> {
                    onRandomCharacterSelect()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterCreatorScreen(
    state: CharacterCreatorState,
    onEvent: (CharacterCreatorEvent) -> Unit
) {
    MainScaffold(
        title = "Character Creator",
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
                    CharacterCreatorTitle(
                        "Character Creator Screen"
                    )
                }
                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            CharacterCreatorButton(
                                modifier = Modifier.weight(1f),
                                text = "Randomize",
                                onClick = {
                                    println("CharacterCreatorScreen")
                                }
                            )
                            CharacterCreatorButton(
                                modifier = Modifier.weight(1f),
                                text = "Create",
                                onClick = {
                                    println("CharacterCreatorScreen")
                                    onEvent(
                                        CharacterCreatorEvent.OnCreateCharacterSelect
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterCreatorScreenPreview() {
    CharacterCreatorScreen(
        state = CharacterCreatorState(),
        onEvent = {}
    )
}
