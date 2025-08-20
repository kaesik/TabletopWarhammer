package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterClassAndCareerScreenRoot(
    viewModel: AndroidCharacterClassAndCareerViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    speciesName: String,
    onClassSelect: (ClassItem) -> Unit,
    onCareerSelect: (CareerItem) -> Unit,
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

    // Initialize class list when the screen is launched
    LaunchedEffect(Unit) {
        viewModel.onEvent(CharacterClassAndCareerEvent.InitClassList)
    }

    // If a class or career is already selected, set them in the view model
    LaunchedEffect(creatorState.selectedClass) {
        val classItem = creatorState.selectedClass ?: return@LaunchedEffect
        viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectedClass(classItem))
        viewModel.onEvent(
            CharacterClassAndCareerEvent.InitCareerList(
                speciesName = speciesName,
                className = classItem.name
            )
        )
    }

    // If a career is already selected, set it in the view model
    LaunchedEffect(creatorState.selectedCareer) {
        creatorState.selectedCareer?.let {
            viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectedCareer(it))
        }
    }

    // If a career path is already selected, set it in the view model
    LaunchedEffect(state.selectedClass, state.canSelectClass) {
        val selectedClass = state.selectedClass ?: return@LaunchedEffect
        if (creatorState.selectedClass?.id == selectedClass.id) return@LaunchedEffect

        val isManualSelection = state.canSelectClass

        // If the class was changed manually remove the experience points
        if (isManualSelection && creatorState.hasRolledClassAndCareer && !creatorState.hasChosenClassAndCareer) {
            creatorViewModel.onEvent(CharacterCreatorEvent.RemoveExperience(35))
            creatorViewModel.onEvent(CharacterCreatorEvent.SetHasChosenClassAndCareer(true))
            creatorViewModel.onEvent(
                CharacterCreatorEvent.ShowMessage("Changed class to: ${selectedClass.name} (−35 XP)")
            )
        }
        // If the class was rolled, just show a message
        else if (isManualSelection) {
            creatorViewModel.onEvent(
                CharacterCreatorEvent.ShowMessage("Selected class: ${selectedClass.name}")
            )
        }

        // Update the creator view model with the selected class
        creatorViewModel.onEvent(CharacterCreatorEvent.SetClass(selectedClass))
        creatorViewModel.onEvent(CharacterCreatorEvent.SetCareer(null, null))
        onClassSelect(selectedClass)

        // Initialize the career list based on the selected class and species
        viewModel.onEvent(
            CharacterClassAndCareerEvent.InitCareerList(
                speciesName = speciesName,
                className = selectedClass.name
            )
        )
    }

    // If a career is selected, update the creator view model and notify the listener
    LaunchedEffect(state.selectedCareer, state.selectedPath, state.canSelectCareer) {
        val selectedCareer = state.selectedCareer ?: return@LaunchedEffect
        val careerPath = state.selectedPath
        if (creatorState.selectedCareer?.id == selectedCareer.id &&
            creatorState.selectedClass?.id == state.selectedClass?.id
        ) return@LaunchedEffect

        creatorViewModel.onEvent(CharacterCreatorEvent.SetCareer(selectedCareer, careerPath))
        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("Selected career: ${selectedCareer.name}"))
        onCareerSelect(selectedCareer)
    }

    CharacterClassAndCareerScreen(
        state = state,
        hasRolledClassAndCareer = creatorState.hasRolledClassAndCareer,
        hasChosenClassAndCareer = creatorState.hasChosenClassAndCareer,
        onEvent = { event ->
            when (event) {
                // Roll a random class and career
                is CharacterClassAndCareerEvent.OnClassAndCareerRoll -> {

                    // Roll a random class and career
                    viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectingClass(false))
                    viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectingCareer(false))
                    viewModel.onEvent(CharacterClassAndCareerEvent.OnClassAndCareerRoll(speciesName = speciesName))

                    // Update the creator view model with the rolled class and career
                    creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(35))
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetHasRolledClassAndCareer(true))
                    creatorViewModel.onEvent(CharacterCreatorEvent.SetHasChosenClassAndCareer(false))
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.ShowMessage("Random roll applied (+35 XP)")
                    )
                }

                is CharacterClassAndCareerEvent.OnNextClick -> if (state.selectedCareer != null) onNextClick()

                else -> viewModel.onEvent(event)
            }
        },
        classes = state.classList,
        careers = state.careerList,
        speciesName = speciesName,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CharacterClassAndCareerScreen(
    state: CharacterClassAndCareerState,
    hasRolledClassAndCareer: Boolean,
    hasChosenClassAndCareer: Boolean,
    onEvent: (CharacterClassAndCareerEvent) -> Unit,
    classes: List<ClassItem>,
    careers: List<CareerItem>,
    speciesName: String,
    snackbarHostState: SnackbarHostState,
) {
    MainScaffold(
        title = "Class & Career",
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
                // Button to roll a random class and career or choose one
                item {
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        CharacterCreatorButton(
                            text = "Random +35XP",
                            onClick = {
                                onEvent(
                                    CharacterClassAndCareerEvent.OnClassAndCareerRoll(
                                        speciesName = speciesName
                                    )
                                )
                            },
                            enabled = !hasRolledClassAndCareer
                        )
                        val chooseLabel = if (hasRolledClassAndCareer && !hasChosenClassAndCareer)
                            "Choose -35XP" else "Choose +0XP"

                        CharacterCreatorButton(
                            text = chooseLabel,
                            onClick = { onEvent(CharacterClassAndCareerEvent.SetSelectingClass(true)) },
                            enabled = !state.canSelectClass
                        )
                    }
                }

                // Display possible classes based on species
                item {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        CharacterCreatorButton(
                            text = state.selectedClass?.name ?: "Select Class",
                            onClick = { expanded = true },
                            enabled = state.canSelectClass
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            classes.forEach { classItem ->
                                DropdownMenuItem(
                                    text = { Text(classItem.name) },
                                    onClick = {
                                        expanded = false
                                        onEvent(
                                            CharacterClassAndCareerEvent.OnClassSelect(
                                                classItem.id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                // Display possible careers based on selected class
                item {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        CharacterCreatorButton(
                            text = state.selectedCareer?.name ?: "Select Career",
                            onClick = {
                                if (state.selectedClass != null) expanded = true
                            },
                            enabled = state.canSelectCareer
                        )
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            careers.forEach { careerItem ->
                                DropdownMenuItem(
                                    text = { Text(careerItem.name) },
                                    onClick = {
                                        expanded = false
                                        onEvent(
                                            CharacterClassAndCareerEvent.OnCareerSelect(
                                                careerItem.id
                                            )
                                        )
                                    }
                                )
                            }
                        }
                    }
                }

                // If a class and career is selected, show a next button
                item {
                    CharacterCreatorButton(
                        text = "Next",
                        onClick = { onEvent(CharacterClassAndCareerEvent.OnNextClick) },
                        enabled = !state.isLoading && state.selectedCareer != null
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterClassAndCareerScreenPreview() {
    CharacterClassAndCareerScreen(
        state = CharacterClassAndCareerState(),
        hasRolledClassAndCareer = false,
        hasChosenClassAndCareer = false,
        onEvent = {},
        classes = listOf(),
        careers = listOf(),
        snackbarHostState = remember { SnackbarHostState() },
        speciesName = "Human"
    )
}
