package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.DetailInputBasic
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.DetailInputRandom
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateRandomAge
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateRandomEyeColor
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateRandomHairColor
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components.generateRandomHeight
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterDetailsScreenRoot(
    viewModel: AndroidCharacterDetailsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val character = creatorViewModel.state.value.character

    CharacterDetailsScreen(
        state = state,
        species = character.species,
        onEvent = { event ->
            when (event) {
                is CharacterDetailsEvent.OnNextClick -> {
                    val species = creatorViewModel.state.value.character.species.uppercase()
                    val finalName = when (species) {
                        "HALFLING" ->
                            listOf(state.name, state.forename, state.clan)
                                .filter { it.isNotBlank() }
                                .joinToString(" ")

                        "HUMAN" ->
                            listOf(state.name, state.surname)
                                .filter { it.isNotBlank() }
                                .joinToString(" ")

                        "DWARF" ->
                            listOf(state.name, state.forename, state.surname, state.clan)
                                .filter { it.isNotBlank() }
                                .joinToString(" ")

                        "HIGH ELF", "WOOD ELF" ->
                            listOf(state.name, state.forename, state.epithet)
                                .filter { it.isNotBlank() }
                                .joinToString(" ")

                        else -> state.name
                    }

                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetCharacterDetails(
                            name = finalName,
                            age = state.age,
                            height = state.height,
                            hairColor = state.hairColor,
                            eyeColor = state.eyeColor,
                        )
                    )
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun CharacterDetailsScreen(
    state: CharacterDetailsState,
    species: String,
    onEvent: (CharacterDetailsEvent) -> Unit
) {
    val race = species.uppercase()

    MainScaffold(
        title = "Details",
        isLoading = state.isLoading,
        isError = state.isError,
        error = state.error,
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                item {
                    CharacterCreatorTitle("Character Creator Screen")
                }

                when (race) {
                    "HALFLING" -> {
                        item {
                            DetailInputRandom(
                                label = "Name",
                                value = state.name,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnNameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputBasic(
                                label = "Forename",
                                value = state.forename,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnForenameChanged(it)) },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Clan",
                                value = state.clan,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnClanChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                    }

                    "DWARF" -> {
                        item {
                            DetailInputRandom(
                                label = "Name",
                                value = state.name,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnNameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Forename",
                                value = state.forename,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnForenameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Surname",
                                value = state.surname,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnForenameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Clan",
                                value = state.clan,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnClanChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                    }

                    "HUMAN" -> {
                        item {
                            DetailInputRandom(
                                label = "Name",
                                value = state.name,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnNameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Surname",
                                value = state.surname,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnSurnameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                    }

                    in listOf("HIGH ELF", "WOOD ELF") -> {
                        item {
                            DetailInputRandom(
                                label = "Name",
                                value = state.name,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnNameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputRandom(
                                label = "Forename",
                                value = state.forename,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnForenameChanged(it)) },
                                onRandomClick = {
                                    onEvent(
                                        CharacterDetailsEvent.GenerateRandomName(
                                            species
                                        )
                                    )
                                },
                            )
                        }
                        item {
                            DetailInputBasic(
                                label = "Epithet",
                                value = state.epithet,
                                onValueChange = { onEvent(CharacterDetailsEvent.OnEpithetChanged(it)) },
                            )
                        }
                    }
                }

                item {
                    DetailInputRandom(
                        label = "Age",
                        value = state.age,
                        onValueChange = { onEvent(CharacterDetailsEvent.OnAgeChanged(it)) },
                        onRandomClick = {
                            val random = generateRandomAge(species)
                            onEvent(CharacterDetailsEvent.OnAgeChanged(random))
                        },
                    )
                }
                item {
                    DetailInputRandom(
                        label = "Height",
                        value = state.height,
                        onValueChange = { onEvent(CharacterDetailsEvent.OnHeightChanged(it)) },
                        onRandomClick = {
                            val random = generateRandomHeight(species)
                            onEvent(CharacterDetailsEvent.OnHeightChanged(random))
                        },
                    )
                }
                item {
                    DetailInputRandom(
                        label = "Hair Color",
                        value = state.hairColor,
                        onValueChange = { onEvent(CharacterDetailsEvent.OnHairColorChanged(it)) },
                        onRandomClick = {
                            val random = generateRandomHairColor(species)
                            onEvent(CharacterDetailsEvent.OnHairColorChanged(random))
                        },
                    )
                }
                item {
                    DetailInputRandom(
                        label = "Eyes Color",
                        value = state.eyeColor,
                        onValueChange = { onEvent(CharacterDetailsEvent.OnEyeColorChanged(it)) },
                        onRandomClick = {
                            val random = generateRandomEyeColor(species)
                            onEvent(CharacterDetailsEvent.OnEyeColorChanged(random))
                        },
                    )
                }
                item {
                    CharacterCreatorButton(
                        text = "Next",
                        onClick = { onEvent(CharacterDetailsEvent.OnNextClick) }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun CharacterDetailsScreenPreview() {
    CharacterDetailsScreen(
        state = CharacterDetailsState(),
        onEvent = {},
        species = "Human"
    )
}
