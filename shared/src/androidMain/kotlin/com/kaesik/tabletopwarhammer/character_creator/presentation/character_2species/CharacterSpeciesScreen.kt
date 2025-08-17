package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterSpeciesScreenRoot(
    viewModel: AndroidCharacterSpeciesViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onSpeciesSelect: (SpeciesItem) -> Unit,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Handle messages from the creatorViewModel
    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

    // Initialize species list and set selected species if already chosen
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterSpeciesEvent.InitSpeciesList)

        // If a species is already selected in the creator state, set it in the species view model
        creatorState.selectedSpecies?.let { speciesItem ->
            viewModel.onEvent(CharacterSpeciesEvent.SetSelectedSpecies(speciesItem))
        }
    }

    // Get the species selection from the view model
    LaunchedEffect(state.pendingSpeciesSelection) {
        val selection = state.pendingSpeciesSelection ?: return@LaunchedEffect

        // Set the species and experience in the creator view model
        creatorViewModel.onEvent(CharacterCreatorEvent.SetSpecies(selection.speciesItem))
        when {
            selection.exp > 0 -> creatorViewModel.onEvent(
                CharacterCreatorEvent.AddExperience(
                    selection.exp
                )
            )

            selection.exp < 0 -> creatorViewModel.onEvent(
                CharacterCreatorEvent.RemoveExperience(
                    -selection.exp
                )
            )
        }
        if (selection.message.isNotBlank()) {
            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(selection.message))
        }
        onSpeciesSelect(selection.speciesItem)

        // Consume the species selection
        viewModel.onEvent(CharacterSpeciesEvent.OnSpeciesSelectionConsumed)
    }

    // Get the random selection from the view model
    LaunchedEffect(state.pendingRandomSpecies) {
        val selection = state.pendingRandomSpecies ?: return@LaunchedEffect

        // Set the species and experience in the creator view model
        creatorViewModel.onEvent(CharacterCreatorEvent.SetSpecies(selection.speciesItem))
        if (selection.exp > 0) {
            creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(selection.exp))
        }
        if (selection.message.isNotBlank()) {
            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(selection.message))
        }
        onSpeciesSelect(selection.speciesItem)

        // Consume the random species selection
        viewModel.onEvent(CharacterSpeciesEvent.OnRandomSpeciesConsumed)
    }

    CharacterSpeciesScreen(
        state = state,
        onEvent = { event ->
            when (event) {

                // Select a species from the list
                is CharacterSpeciesEvent.OnSpeciesSelect -> {
                    viewModel.onEvent(
                        CharacterSpeciesEvent.OnSpeciesSelect(
                            id = event.id,
                            currentSpeciesId = creatorState.selectedSpecies?.id
                        )
                    )
                }

                // Roll a random species
                is CharacterSpeciesEvent.OnSpeciesRoll -> {
                    viewModel.onEvent(CharacterSpeciesEvent.SetSelectingSpecies(false))
                    viewModel.onEvent(
                        CharacterSpeciesEvent.OnSpeciesRoll(
                            currentSpeciesId = creatorState.selectedSpecies?.id
                        )
                    )
                }

                // Toggle species selection mode
                is CharacterSpeciesEvent.SetSelectingSpecies -> viewModel.onEvent(event)

                // If species is selected go to next
                is CharacterSpeciesEvent.OnNextClick -> if (state.selectedSpecies != null) onNextClick()

                else -> viewModel.onEvent(event)
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
    MainScaffold(
        title = "Species",
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
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
                // Button to roll a random species or choose one
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CharacterCreatorButton(
                            text = "Random +25XP",
                            onClick = { onEvent(CharacterSpeciesEvent.OnSpeciesRoll()) },
                            enabled = !state.hasRolledSpecies
                        )
                        CharacterCreatorButton(
                            text = if (state.hasRolledSpecies) "Choose -25XP" else "Choose +0XP",
                            onClick = { onEvent(CharacterSpeciesEvent.SetSelectingSpecies(true)) },
                            enabled = !state.canSelectSpecies
                        )
                    }
                }

                // Display the selected species or a message if none is selected
                items(species) { speciesItem ->
                    CharacterCreatorButton(
                        text = speciesItem.name,
                        onClick = { onEvent(CharacterSpeciesEvent.OnSpeciesSelect(speciesItem.id)) },
                        isSelected = state.selectedSpecies?.id == speciesItem.id,
                        enabled = state.canSelectSpecies && (state.selectedSpecies?.id != speciesItem.id),
                    )
                }

                // If no species is selected, show a next button
                item {
                    CharacterCreatorButton(
                        text = "Next",
                        onClick = { onEvent(CharacterSpeciesEvent.OnNextClick) },
                        enabled = state.selectedSpecies != null
                    )
                }
            }
        }
    )
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
