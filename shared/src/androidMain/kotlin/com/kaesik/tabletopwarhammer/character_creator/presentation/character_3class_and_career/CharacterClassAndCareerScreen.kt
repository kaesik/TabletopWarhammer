package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterClassAndCareerScreenRoot(
    viewModel: AndroidCharacterClassAndCareerViewModel = koinViewModel(),
    onClassSelect: () -> Unit,
    onCareerSelect: () -> Unit,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterClassAndCareerEvent.InitClassList)
        viewModel.onEvent(CharacterClassAndCareerEvent.InitCareerList)
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
                    onNextClick()
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
    classes: List<String>,
    careers: List<String>,
) {
    Scaffold(

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
                CharacterCreatorTitle("Character ClassAndCareer Screen")
            }
            item {
                DiceThrow(
                    onClick = {}
                )
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                Box() {
                    CharacterCreatorButton(
                        text = "Select Class",
                        onClick = { expanded = !expanded }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        classes.forEach { className ->
                            DropdownMenuItem(
                                text = { Text(className) },
                                onClick = { }
                            )
                        }
                    }
                }
            }
            item {
                var expanded by remember { mutableStateOf(false) }
                Box {
                    CharacterCreatorButton(
                        text = "Select Career",
                        onClick = { expanded = !expanded }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                    ) {
                        careers.forEach { careerName ->
                            DropdownMenuItem(
                                text = { Text(careerName) },
                                onClick = { }
                            )
                        }
                    }
                }
            }
            item {
                CharacterCreatorButton(
                    text = "Next",
                    onClick = {
                        println("CharacterClassAndCareerScreen")
                        onEvent(CharacterClassAndCareerEvent.OnNextClick)
                    }
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
        classes = listOf("Class 1", "Class 2", "Class 3"),
        careers = listOf("Career 1", "Career 2", "Career 3"),
    )
}
