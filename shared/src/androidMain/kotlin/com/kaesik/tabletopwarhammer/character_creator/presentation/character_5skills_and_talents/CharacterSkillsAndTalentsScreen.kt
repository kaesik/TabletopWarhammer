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
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterItem
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SkillsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.TalentsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun CharacterSkillsAndTalentsScreenRoot(
    viewModel: AndroidCharacterSkillsAndTalentsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel,
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val character = creatorViewModel.state.value.character

    LaunchedEffect(true) {
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitSkillsList(
                from = SpeciesOrCareer.SPECIES,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
        viewModel.onEvent(
            CharacterSkillsAndTalentsEvent.InitTalentsList(
                from = SpeciesOrCareer.SPECIES,
                speciesName = character.species,
                careerPathName = character.careerPath
            )
        )
    }

    val skills = state.skillList
    val talents = state.talentList
    val currentSkills = when (state.speciesOrCareer) {
        SpeciesOrCareer.SPECIES -> skills.getOrNull(0) ?: emptyList()
        SpeciesOrCareer.CAREER -> skills.getOrNull(1) ?: emptyList()
    }
    val currentTalents = when (state.speciesOrCareer) {
        SpeciesOrCareer.SPECIES -> talents.getOrNull(0) ?: emptyList()
        SpeciesOrCareer.CAREER -> talents.getOrNull(1) ?: emptyList()
    }

    CharacterSkillsAndTalentsScreen(
        state = state,
        skills = currentSkills,
        talents = currentTalents,
        onSkillChecked = { skill, isChecked ->
            viewModel.onEvent(CharacterSkillsAndTalentsEvent.OnSkillChecked(skill, isChecked))
        },
        onEvent = { event ->
            when (event) {
                is CharacterSkillsAndTalentsEvent.OnNextClick -> {
                    val allSkills = state.skillList.flatten()
                    val allTalents = state.talentList.flatten()

                    val basicSkills = allSkills
                        .filter { it.isBasic == true }
                        .map {
                            listOf(
                                it.name,
                                it.attribute.orEmpty(),
                                "0",
                                getAttributeValue(character, it.attribute.orEmpty()).toString()
                            )
                        }

                    val advancedSkills = allSkills
                        .filter { it.isBasic != true }
                        .map {
                            listOf(
                                it.name,
                                it.attribute.orEmpty(),
                                "0",
                                getAttributeValue(character, it.attribute.orEmpty()).toString()
                            )
                        }

                    val mappedTalents = allTalents.map {
                        listOf(it.name, it.source.orEmpty(), it.page?.toString() ?: "")
                    }

                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetSkillsAndTalents(
                            basicSkills = basicSkills,
                            advancedSkills = advancedSkills,
                            talents = mappedTalents
                        )
                    )

                    onNextClick()
                }

                else -> viewModel.onEvent(event)
            }
        }
    )
}

private fun getAttributeValue(character: CharacterItem, attributeName: String): Int {
    return when (attributeName) {
        "Weapon Skill" -> character.weaponSkill.firstOrNull() ?: 0
        "Ballistic Skill" -> character.ballisticSkill.firstOrNull() ?: 0
        "Strength" -> character.strength.firstOrNull() ?: 0
        "Toughness" -> character.toughness.firstOrNull() ?: 0
        "Initiative" -> character.initiative.firstOrNull() ?: 0
        "Agility" -> character.agility.firstOrNull() ?: 0
        "Dexterity" -> character.dexterity.firstOrNull() ?: 0
        "Intelligence" -> character.intelligence.firstOrNull() ?: 0
        "Willpower" -> character.willPower.firstOrNull() ?: 0
        "Fellowship" -> character.fellowship.firstOrNull() ?: 0
        else -> 0
    }
}

@Composable
fun CharacterSkillsAndTalentsScreen(
    state: CharacterSkillsAndTalentsState,
    skills: List<SkillItem>,
    talents: List<TalentItem>,
    onSkillChecked: (SkillItem, Boolean) -> Unit,
    onEvent: (CharacterSkillsAndTalentsEvent) -> Unit,
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
                CharacterCreatorTitle("Character Skills and Talents")
            }
            item {
                SkillsTable(
                    skills = skills,
                    selectedSkills = state.selectedSkills,
                    onSkillChecked = onSkillChecked,
                )
            }
            item {
                TalentsTable(
                    talents = talents
                )
            }
            item {
                if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                    CharacterCreatorButton(
                        text = "Next: Career Skills",
                        onClick = {
                            onEvent(CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick)
                        }
                    )
                } else {
                    CharacterCreatorButton(
                        text = "Next: Trappings",
                        onClick = {
                            onEvent(CharacterSkillsAndTalentsEvent.OnNextClick)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CharacterSkillsAndTalentsScreenPreview() {
    CharacterSkillsAndTalentsScreen(
        state = CharacterSkillsAndTalentsState(),
        skills = listOf(),
        talents = listOf(),
        onSkillChecked = { _, _ -> },
        onEvent = {},
    )
}
