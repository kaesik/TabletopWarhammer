package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.kaesik.tabletopwarhammer.core.presentation.components.WarhammerSnackbarHost
import com.kaesik.tabletopwarhammer.core.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.core.presentation.components.showWarhammerSnackbar
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo
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
            snackbarHostState.showWarhammerSnackbar(
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

    CharacterSpeciesScreen(
        state = state,
        hasRolledSpecies = creatorState.hasRolledSpecies,
        hasChosenSpecies = creatorState.hasChosenSpecies,
        onEvent = { event ->
            when (event) {

                // Select a species from the list
                is CharacterSpeciesEvent.OnSpeciesSelect -> {
                    val selected = state.speciesList.firstOrNull { it.id == event.id }
                        ?: return@CharacterSpeciesScreen
                    val prevId = creatorState.selectedSpecies?.id
                    if (selected.id == prevId) return@CharacterSpeciesScreen

                    // Select species
                    viewModel.onEvent(CharacterSpeciesEvent.OnSpeciesSelect(event.id))

                    // Update the creator view model with the selected species
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetSpecies(selected))
                    if (creatorState.hasRolledSpecies && !creatorState.hasChosenSpecies) {
                        creatorViewModel.onEvent(CharacterCreatorEvent.RemoveExperience(25))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetHasChosenSpecies(true))
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("Changed species to: ${selected.name} (-25 XP)")
                        )
                    } else {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("Selected species: ${selected.name}")
                        )
                    }
                    onSpeciesSelect(selected)
                }

                // Roll a random species
                is CharacterSpeciesEvent.OnSpeciesRoll -> {
                    if (creatorState.hasRolledSpecies) return@CharacterSpeciesScreen

                    // Roll a random species
                    viewModel.onEvent(CharacterSpeciesEvent.OnSpeciesRoll)
                    val picked =
                        viewModel.state.value.selectedSpecies ?: return@CharacterSpeciesScreen

                    // Update the creator view model with the rolled species
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetSpecies(picked))
                    creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(25))
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetHasRolledSpecies(true))
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetHasChosenSpecies(false))
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.ShowMessage("Randomly selected species: ${picked.name} (+25 XP)")
                    )
                    onSpeciesSelect(picked)
                }

                // If species is selected go to next
                is CharacterSpeciesEvent.OnNextClick -> {
                    if (creatorState.selectedSpecies != null) onNextClick()
                }

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
    hasRolledSpecies: Boolean,
    hasChosenSpecies: Boolean,
    onEvent: (CharacterSpeciesEvent) -> Unit,
    species: List<SpeciesItem>,
    snackbarHostState: SnackbarHostState,
) {
    MainScaffold(
        title = "Species",
        snackbarHost = { WarhammerSnackbarHost(snackbarHostState) },
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
                            onClick = { onEvent(CharacterSpeciesEvent.OnSpeciesRoll) },
                            enabled = !hasRolledSpecies
                        )

                        val chooseLabel = if (hasRolledSpecies && !hasChosenSpecies) "Choose -25XP"
                        else "Choose"

                        CharacterCreatorButton(
                            text = chooseLabel,
                            onClick = { onEvent(CharacterSpeciesEvent.SetSelectingSpecies(true)) },
                            enabled = !state.canSelectSpecies
                        )
                    }
                }

                // Display the selected species or a message if none is selected
                items(species) { speciesItem ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CharacterCreatorButton(
                                text = speciesItem.name,
                                onClick = {
                                    onEvent(CharacterSpeciesEvent.OnSpeciesSelect(speciesItem.id))
                                },
                                isSelected = state.selectedSpecies?.id == speciesItem.id,
                                enabled = state.canSelectSpecies && (state.selectedSpecies?.id != speciesItem.id),
                            )
                        }

                        // Info icon to inspect the species details
                        val openInfo = LocalOpenInfo.current
                        InspectInfoIcon(
                            onClick = {
                                openInfo(
                                    InspectRef(
                                        type = LibraryEnum.SPECIES,
                                        key = speciesItem.name
                                    )
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(start = 256.dp)
                        )
                    }
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
        hasRolledSpecies = false,
        hasChosenSpecies = false,
        onEvent = {},
        species = listOf(),
        snackbarHostState = remember { SnackbarHostState() }
    )
}
