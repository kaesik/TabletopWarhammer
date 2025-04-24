package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Scaffold
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.DiceThrow
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterClassAndCareerScreenRoot(
    viewModel: AndroidCharacterClassAndCareerViewModel = koinViewModel(),
    speciesName: String,
    onClassSelect: () -> Unit,
    onCareerSelect: () -> Unit,
    onNextClick: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterClassAndCareerEvent.InitClassList)
    }
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterClassAndCareerEvent.InitCareerList(speciesName))
    }
    val classes = state.classList
    val careers = state.careerList
    CharacterClassAndCareerScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterClassAndCareerEvent.OnClassSelect -> {
                    onClassSelect()
                }

                is CharacterClassAndCareerEvent.OnCareerSelect -> {
                    onCareerSelect()
                }

                is CharacterClassAndCareerEvent.OnNextClick -> {
                    state.selectedCareer?.id?.let(onNextClick)
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        },
        classes = classes,
        careers = careers,
    )
}

@Composable
fun CharacterClassAndCareerScreen(
    state: CharacterClassAndCareerState,
    onEvent: (CharacterClassAndCareerEvent) -> Unit,
    classes: List<ClassItem>,
    careers: List<CareerItem>,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                CharacterCreatorTitle("Character ClassAndCareer Screen")
            }

            item {
                DiceThrow(onClick = {})
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
                                    onEvent(CharacterClassAndCareerEvent.OnCareerSelect(careerItem.id))
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
}


@Preview
@Composable
fun CharacterClassAndCareerScreenPreview() {
    CharacterClassAndCareerScreen(
        state = CharacterClassAndCareerState(),
        onEvent = {},
        classes = listOf(),
        careers = listOf(),
    )
}
