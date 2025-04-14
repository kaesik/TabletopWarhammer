package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.CharacterSkillsAndTalentsEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.TrappingsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.Button1
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterTrappingsScreenRoot(
    viewModel: AndroidCharacterTrappingsViewModel = koinViewModel(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(
            CharacterTrappingsEvent.InitTrappingsList(
                ClassOrCareer.CAREER
            )
        )
    }
    val trappings = state.trappingList
    CharacterTrappingsScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterTrappingsEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        },
        trappings = trappings,
    )
}

@Composable
fun CharacterTrappingsScreen(
    state: CharacterTrappingsState,
    onEvent: (CharacterTrappingsEvent) -> Unit,
    trappings:  List<String>,
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
                Text("Character Trappings Screen")
            }
            item {
                TrappingsTable(
                    trappings = trappings
                )
            }
            item {
                Button1(
                    text = "Next",
                    onClick = {
                        println("CharacterTrappingsScreen")
                        onEvent(CharacterTrappingsEvent.OnNextClick)
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun CharacterTrappingsScreenPreview() {
    CharacterTrappingsScreen(
        state = CharacterTrappingsState(),
        onEvent = {},
        trappings = listOf(
            "Trapping 1",
            "Trapping 2",
            "Trapping 3",
            "Trapping 4",
            "Trapping 5",
            "Trapping 6",
            "Trapping 7",
            "Trapping 8",
            "Trapping 9",
            "Trapping 10"
        )
    )
}
