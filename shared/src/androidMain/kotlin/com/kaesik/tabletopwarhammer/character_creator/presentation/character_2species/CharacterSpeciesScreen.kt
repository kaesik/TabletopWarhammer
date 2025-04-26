package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.DiceThrow
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSpeciesScreenRoot(
    viewModel: AndroidCharacterSpeciesViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = koinViewModel(),
    onSpeciesSelect: (SpeciesItem) -> Unit,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    LaunchedEffect(true) {
        viewModel.onEvent(CharacterSpeciesEvent.InitSpeciesList)
    }

    CharacterSpeciesScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterSpeciesEvent.OnSpeciesSelect -> {
                    viewModel.onEvent(event)

                    val selectedSpecies = state.speciesList.find { it.id == event.id }
                    if (selectedSpecies != null) {
                        onSpeciesSelect(selectedSpecies)
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetSpecies(selectedSpecies))
                    }
                }

                is CharacterSpeciesEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }
        },
        species = state.speciesList,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CharacterSpeciesScreen(
    state: CharacterSpeciesState,
    onEvent: (CharacterSpeciesEvent) -> Unit,
    species: List<SpeciesItem>,
    snackbarHostState: SnackbarHostState,
) {
    Scaffold(
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                CharacterCreatorTitle("Character Species Screen")
            }
            item {
                DiceThrow(onClick = {})
            }
            items(species) { speciesItem ->
                CharacterCreatorButton(
                    text = speciesItem.name,
                    onClick = {
                        onEvent(CharacterSpeciesEvent.OnSpeciesSelect(speciesItem.id))
                        println("CharacterSpeciesScreen: $speciesItem")
                    }
                )
            }
            item {
                CharacterCreatorButton(
                    text = "Next",
                    onClick = { onEvent(CharacterSpeciesEvent.OnNextClick) },
                    enabled = state.selectedSpecies != null
                )
            }
        }
    }
}

@Preview
@Composable
fun CharacterSpeciesScreenPreview() {
    CharacterSpeciesScreen(
        state = CharacterSpeciesState(),
        onEvent = {},
        species = listOf(),
        snackbarHostState = remember { SnackbarHostState() }
    )
}
