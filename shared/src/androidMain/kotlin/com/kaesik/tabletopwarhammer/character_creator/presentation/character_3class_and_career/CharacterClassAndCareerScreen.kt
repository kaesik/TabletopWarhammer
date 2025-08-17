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

    // Initialize class and career list and set selected class and career if they exist
    LaunchedEffect(creatorState.selectedClass, creatorState.selectedCareer) {
        viewModel.onEvent(CharacterClassAndCareerEvent.InitClassList)

        // If a class or career is already selected, set them in the view model
        creatorState.selectedClass?.let { classItem ->
            viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectedClass(classItem))

            // Initialize career list based on selected class
            viewModel.onEvent(
                CharacterClassAndCareerEvent.InitCareerList(
                    speciesName = creatorViewModel.state.value.character.species,
                    className = classItem.name
                )
            )
        }

        // If a career is already selected, set it in the view model
        creatorState.selectedCareer?.let { careerItem ->
            viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectedCareer(careerItem))
        }
    }

    // Get the class selection from the view model
    LaunchedEffect(state.pendingClassSelection) {
        val selection = state.pendingClassSelection ?: return@LaunchedEffect

        // Set the selected class in the creator view model
        creatorViewModel.onEvent(CharacterCreatorEvent.SetClass(selection.classItem))
        creatorViewModel.onEvent(CharacterCreatorEvent.SetCareer(null, null))
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
        onClassSelect(selection.classItem)

        // Get the career list based on the selected class
        viewModel.onEvent(
            CharacterClassAndCareerEvent.InitCareerList(
                speciesName = creatorViewModel.state.value.character.species,
                className = selection.classItem.name
            )
        )

        // Consume the class selection
        viewModel.onEvent(CharacterClassAndCareerEvent.OnClassSelectionConsumed)
    }

    // Get the career selection from the view model
    LaunchedEffect(state.pendingCareerSelection) {
        val selection = state.pendingCareerSelection ?: return@LaunchedEffect

        // Set the selected career in the creator view model
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetCareer(
                careerItem = selection.careerItem,
                careerPathItem = selection.careerPathItem
            )
        )
        if (selection.message.isNotBlank()) {
            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(selection.message))
        }
        onCareerSelect(selection.careerItem ?: return@LaunchedEffect)

        // Consume the career selection
        viewModel.onEvent(CharacterClassAndCareerEvent.OnCareerSelectionConsumed)
    }

    // Get the random selection from the view model
    LaunchedEffect(state.pendingRandomSelection) {
        val selection = state.pendingRandomSelection ?: return@LaunchedEffect

        // Set the selected class and career in the creator view model
        creatorViewModel.onEvent(CharacterCreatorEvent.SetClass(selection.classItem))
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetCareer(
                selection.careerItem,
                selection.careerPathItem
            )
        )
        if (selection.exp > 0) {
            creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(selection.exp))
        }
        if (selection.message.isNotBlank()) {
            creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage(selection.message))
        }
        onClassSelect(selection.classItem)
        onCareerSelect(selection.careerItem ?: return@LaunchedEffect)

        // Consume the random selection
        viewModel.onEvent(CharacterClassAndCareerEvent.OnRandomSelectionConsumed)
    }

    CharacterClassAndCareerScreen(
        state = state,
        onEvent = { event ->
            when (event) {

                // Select a class and career from the list
                is CharacterClassAndCareerEvent.OnClassSelect -> {
                    viewModel.onEvent(
                        CharacterClassAndCareerEvent.OnClassSelect(
                            id = event.id,
                            currentClassId = creatorState.selectedClass?.id
                        )
                    )
                }

                is CharacterClassAndCareerEvent.OnCareerSelect -> {
                    viewModel.onEvent(
                        CharacterClassAndCareerEvent.OnCareerSelect(
                            id = event.id,
                            currentCareerId = creatorState.selectedCareer?.id
                        )
                    )
                }

                // Roll a random class and career
                is CharacterClassAndCareerEvent.OnClassAndCareerRoll -> {
                    viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectingClass(false))
                    viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectingCareer(false))
                    viewModel.onEvent(
                        CharacterClassAndCareerEvent.OnClassAndCareerRoll(
                            speciesName = speciesName,
                            currentClassId = creatorState.selectedClass?.id,
                            currentCareerId = creatorState.selectedCareer?.id
                        )
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
                            enabled = !state.hasRolledClassAndCareer
                        )
                        CharacterCreatorButton(
                            text = if (state.hasRolledClassAndCareer) "Choose -35XP" else "Choose +0XP",
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
        onEvent = {},
        classes = listOf(),
        careers = listOf(),
        snackbarHostState = remember { SnackbarHostState() },
        speciesName = "Human"
    )
}
