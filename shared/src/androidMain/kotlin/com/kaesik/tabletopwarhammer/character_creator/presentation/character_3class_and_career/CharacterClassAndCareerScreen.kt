package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.DiceThrow
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import kotlinx.coroutines.launch
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
    val coroutineScope = rememberCoroutineScope() // ?

    // Handle messages from the creatorViewModel
    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
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

    CharacterClassAndCareerScreen(
        state = state,
        onEvent = { event ->
            when (event) {

                // Initialize and set class
                is CharacterClassAndCareerEvent.OnClassSelect -> {
                    viewModel.onEvent(event)

                    val selectedClass = state.classList.find { it.id == event.id }
                    val currentClassId = creatorState.selectedClass?.id

                    // Check if the selected class is different from the current class
                    if (selectedClass != null && selectedClass.id != currentClassId) {
                        println(selectedClass.id)
                        println(currentClassId)
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetClass(selectedClass))
                        creatorViewModel.onEvent(CharacterCreatorEvent.SetCareer(null, null))
                        viewModel.onEvent(
                            CharacterClassAndCareerEvent.InitCareerList(
                                speciesName = creatorViewModel.state.value.character.species,
                                className = selectedClass.name
                            )
                        )
                        onClassSelect(selectedClass)
                    }
                }

                // Initialize and set career
                is CharacterClassAndCareerEvent.OnCareerSelect -> {
                    viewModel.onEvent(event)

                    val selectedCareer = state.careerList.find { it.id == event.id }
                    val currentCareerId = creatorState.selectedCareer?.id

                    // Check if the selected career is different from the current career
                    if (selectedCareer != null && selectedCareer.id != currentCareerId) {
                        val firstCareerPathName = selectedCareer.careerPath
                            ?.split(",")?.firstOrNull()?.trim()

                        // If the career has a path, fetch it
                        coroutineScope.launch {
                            if (!firstCareerPathName.isNullOrEmpty()) {
                                val careerPathItem = viewModel
                                    .characterCreatorClient
                                    .getCareerPath(pathName = firstCareerPathName)

                                creatorViewModel.onEvent(
                                    CharacterCreatorEvent.SetCareer(
                                        careerItem = selectedCareer,
                                        careerPathItem = careerPathItem
                                    )
                                )
                            }
                            onCareerSelect(selectedCareer)
                        }
                    }
                }

                is CharacterClassAndCareerEvent.RollRandomClassAndCareer -> {
                    val classList = state.classList
                    if (classList.isEmpty()) return@CharacterClassAndCareerScreen

                    val randomClass = classList.random()

                    coroutineScope.launch {
                        val careers = viewModel.characterCreatorClient.getCareers(
                            speciesName,
                            randomClass.name
                        )
                        if (careers.isEmpty()) return@launch

                        val randomCareer = careers.random()
                        val firstCareerPath =
                            randomCareer.careerPath?.split(",")?.firstOrNull()?.trim()

                        val careerPathItem = if (!firstCareerPath.isNullOrEmpty()) {
                            try {
                                viewModel.characterCreatorClient.getCareerPath(firstCareerPath)
                            } catch (e: Exception) {
                                null
                            }
                        } else null

                        creatorViewModel.onEvent(CharacterCreatorEvent.SetClass(randomClass))
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.SetCareer(
                                randomCareer,
                                careerPathItem
                            )
                        )
                        creatorViewModel.onEvent(CharacterCreatorEvent.AddExperience(35))
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("Randomly selected class & career: ${randomClass.name} / ${randomCareer.name} (+35 XP)"))

                        viewModel.onEvent(CharacterClassAndCareerEvent.SetSelectedClass(randomClass))
                        viewModel.onEvent(
                            CharacterClassAndCareerEvent.SetSelectedCareer(
                                randomCareer
                            )
                        )
                        viewModel.onEvent(
                            CharacterClassAndCareerEvent.InitCareerList(
                                speciesName,
                                randomClass.name
                            )
                        )
                        viewModel.onEvent(CharacterClassAndCareerEvent.SetHasRolledClassAndCareer)

                        onClassSelect(randomClass)
                        onCareerSelect(randomCareer)
                    }
                }

                is CharacterClassAndCareerEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }
        },
        classes = state.classList,
        careers = state.careerList,
        snackbarHostState = snackbarHostState
    )
}

@Composable
fun CharacterClassAndCareerScreen(
    state: CharacterClassAndCareerState,
    onEvent: (CharacterClassAndCareerEvent) -> Unit,
    classes: List<ClassItem>,
    careers: List<CareerItem>,
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
                item {
                    CharacterCreatorTitle("Character ClassAndCareer Screen")
                }
                if (!state.hasRolledClassAndCareer) {
                    item {
                        DiceThrow(onClick = { onEvent(CharacterClassAndCareerEvent.RollRandomClassAndCareer) })
                    }
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        CharacterCreatorButton(
                            text = state.selectedClass?.name ?: "Select Class",
                            onClick = { expanded = true },
                            enabled = !state.isLoading
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
                                        onEvent(CharacterClassAndCareerEvent.OnClassSelect(classItem.id))
                                    }
                                )
                            }
                        }
                    }
                }
                item {
                    var expanded by remember { mutableStateOf(false) }
                    Box {
                        CharacterCreatorButton(
                            text = state.selectedCareer?.name ?: "Select Career",
                            onClick = {
                                if (state.selectedClass != null) expanded = true
                            },
                            enabled = !state.isLoading && state.selectedClass != null
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
                item {
                    CharacterCreatorButton(
                        text = "Next",
                        onClick = {
                            onEvent(CharacterClassAndCareerEvent.OnNextClick)
                        },
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
        snackbarHostState = remember { SnackbarHostState() }
    )
}
