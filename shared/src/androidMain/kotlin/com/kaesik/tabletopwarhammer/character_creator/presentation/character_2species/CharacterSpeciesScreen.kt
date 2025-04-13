package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.Button1
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSpeciesScreenRoot(
    viewModel: AndroidCharacterSpeciesViewModel = koinViewModel(),
    onSpeciesSelect: () -> Unit,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(CharacterSpeciesEvent.InitSpeciesList)
    }
    val species = state.speciesList
    CharacterSpeciesScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterSpeciesEvent.OnSpeciesSelect -> {
                    onSpeciesSelect()
                }

                is CharacterSpeciesEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        },
        species = species
    )
}

@Composable
fun CharacterSpeciesScreen(
    state: CharacterSpeciesState,
    onEvent: (CharacterSpeciesEvent) -> Unit,
    species: List<String>
) {
    Scaffold(

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Text("Character Species Screen")
            }
            items(species) {
                Button1(
                    text = it,
                    onClick = {
                        println("CharacterSpeciesScreen: $it")
                    }
                )
            }
            item {
                Button1(
                    text = "Next",
                    onClick = {
                        println("CharacterSpeciesScreen")
                        onEvent(CharacterSpeciesEvent.OnNextClick)
                    }
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
        species = listOf("Human", "Elf", "Dwarf", "Orc", "Goblin")
    )
}
