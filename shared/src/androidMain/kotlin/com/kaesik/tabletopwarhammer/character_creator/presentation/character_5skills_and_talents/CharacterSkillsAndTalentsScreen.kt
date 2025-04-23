package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SkillsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.TalentsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSkillsAndTalentsScreenRoot(
    viewModel: AndroidCharacterSkillsAndTalentsViewModel = koinViewModel(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitSkillsList(
                SpeciesOrCareer.SPECIES
            )
        )
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitTalentsList(
                SpeciesOrCareer.SPECIES
            )
        )
    }
    val skills = state.skillList
    val talents = state.talentList
    val speciesSkills = skills.getOrNull(0) ?: emptyList()
    val speciesTalents = talents.getOrNull(0) ?: emptyList()

    CharacterSkillsAndTalentsScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is CharacterSkillsAndTalentsEvent.OnNextClick -> {
                    onNextClick()
                }

                else -> Unit
            }

            viewModel.onEvent(event)
        },
        skills = speciesSkills,
        talents = speciesTalents,
    )
}

@Composable
fun CharacterSkillsAndTalentsScreen(
    state: CharacterSkillsAndTalentsState,
    onEvent: (CharacterSkillsAndTalentsEvent) -> Unit,
    skills: List<String>,
    talents: List<String>,
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
                CharacterCreatorTitle("Character SkillsAndTalents Screen")
            }
            item {
                SkillsTable(
                    skills = skills
                )
            }
            item {
                TalentsTable(
                    talents = talents
                )
            }
            item {
                CharacterCreatorButton(
                    text = "Next",
                    onClick = {
                        println("CharacterSkillsAndTalentsScreen")
                        onEvent(CharacterSkillsAndTalentsEvent.OnNextClick)
                    }
                )
            }
        }

    }
}

@Preview
@Composable
fun CharacterSkillsAndTalentsScreenPreview() {
    CharacterSkillsAndTalentsScreen(
        state = CharacterSkillsAndTalentsState(),
        onEvent = {},
        skills = listOf(),
        talents = listOf()
    )
}
