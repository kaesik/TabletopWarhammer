package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import TalentsTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterSkillsAndTalentsScreenRoot(
    viewModel: AndroidCharacterSkillsAndTalentsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val creatorState by creatorViewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val character = creatorViewModel.state.value.character

    LaunchedEffect(creatorState.message, creatorState.isError) {
        creatorState.message?.let { message ->
            snackbarHostState.showCharacterCreatorSnackbar(
                message = message,
                type = if (creatorState.isError == true) SnackbarType.Error else SnackbarType.Success
            )
            creatorViewModel.onEvent(CharacterCreatorEvent.ClearMessage)
        }
    }

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

    val currentSkills = when (state.speciesOrCareer) {
        SpeciesOrCareer.SPECIES -> state.skillList.getOrNull(0) ?: emptyList()
        SpeciesOrCareer.CAREER -> state.skillList.getOrNull(1) ?: emptyList()
    }

    fun handleSaveSkillsAndTalents(
        viewModel: AndroidCharacterSkillsAndTalentsViewModel,
        creatorViewModel: AndroidCharacterCreatorViewModel,
        character: CharacterItem
    ) {
        val currentState = viewModel.state.value

        val basicSkills = currentState.selectedSkills3.map {
            listOf(
                it.name,
                it.attribute.orEmpty(),
                "3",
                getAttributeValue(character, it.attribute.orEmpty()).toString()
            )
        }

        val advancedSkills = currentState.selectedSkills5.map {
            listOf(
                it.name,
                it.attribute.orEmpty(),
                "5",
                getAttributeValue(character, it.attribute.orEmpty()).toString()
            )
        }

        val allTalents = currentState.selectedTalents.map {
            listOf(it.name, it.source.orEmpty(), it.page?.toString() ?: "")
        }

        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetSkillsAndTalents(
                basicSkills = basicSkills,
                advancedSkills = advancedSkills,
                talents = allTalents
            )
        )
    }

    CharacterSkillsAndTalentsScreen(
        state = state,
        skills = currentSkills,
        onEvent = { event ->
            when (event) {
                is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> {
                    if (event.isChecked && state.selectedSkills3.size >= 3) {
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("You can only select 3 skills +3!"))
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> {
                    if (event.isChecked && state.selectedSkills5.size >= 3) {
                        creatorViewModel.onEvent(CharacterCreatorEvent.ShowMessage("You can only select 3 skills +5!"))
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                is CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents -> {
                    handleSaveSkillsAndTalents(viewModel, creatorViewModel, character)
                }

                is CharacterSkillsAndTalentsEvent.OnNextClick -> {
                    val basicSkills = state.selectedSkills3.map {
                        listOf(
                            it.name,
                            it.attribute.orEmpty(),
                            "3",
                            getAttributeValue(character, it.attribute.orEmpty()).toString()
                        )
                    }
                    val advancedSkills = state.selectedSkills5.map {
                        listOf(
                            it.name,
                            it.attribute.orEmpty(),
                            "5",
                            getAttributeValue(character, it.attribute.orEmpty()).toString()
                        )
                    }
                    val allTalents = state.selectedTalents.map {
                        listOf(it.name, it.source.orEmpty(), it.page?.toString() ?: "")
                    }
                    creatorViewModel.onEvent(
                        CharacterCreatorEvent.SetSkillsAndTalents(
                            basicSkills,
                            advancedSkills,
                            allTalents
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
                    selectedSkills3 = state.selectedSkills3,
                    selectedSkills5 = state.selectedSkills5,
                    onSkillChecked3 = { skill, isChecked ->
                        onEvent(CharacterSkillsAndTalentsEvent.OnSkillChecked3(skill, isChecked))
                    },
                    onSkillChecked5 = { skill, isChecked ->
                        onEvent(CharacterSkillsAndTalentsEvent.OnSkillChecked5(skill, isChecked))
                    }
                )
            }
            item {
                TalentsTable(
                    talentsGroups = when (state.speciesOrCareer) {
                        SpeciesOrCareer.SPECIES -> state.speciesTalentsList
                        SpeciesOrCareer.CAREER -> state.careerTalentsList
                    },
                    selectedTalents = state.selectedTalents,
                    onTalentChecked = { talent, isChecked ->
                        onEvent(CharacterSkillsAndTalentsEvent.OnTalentChecked(talent, isChecked))
                    }
                )
            }
            item {
                if (state.speciesOrCareer == SpeciesOrCareer.CAREER) {
                    CharacterCreatorButton(
                        text = "Back: Species Skills and Talents",
                        onClick = {
                            onEvent(CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents)
                            onEvent(CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick)
                        }
                    )
                }
            }
            item {
                val buttonText = if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                    "Next: Career Skills and Talents"
                } else {
                    "Next: Trappings"
                }
                CharacterCreatorButton(
                    text = buttonText,
                    onClick = {
                        onEvent(CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents)
                        if (state.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                            onEvent(CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick)
                        } else {
                            onEvent(CharacterSkillsAndTalentsEvent.OnNextClick)
                        }
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
        skills = listOf(),
        onEvent = { }
    )
}
