package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import TalentsTable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SkillsTable
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorSnackbarHost
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorTitle
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.SnackbarType
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.showCharacterCreatorSnackbar
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.presentation.MainScaffold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun CharacterSkillsAndTalentsScreenRoot(
    viewModel: AndroidCharacterSkillsAndTalentsViewModel = koinViewModel(),
    creatorViewModel: AndroidCharacterCreatorViewModel = getKoin().get(),
    onNextClick: () -> Unit,
    onBackClick: () -> Unit
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

    LaunchedEffect(state.speciesOrCareer) {
        if (state.speciesOrCareer == SpeciesOrCareer.CAREER) {
            if (state.careerSkillsList.isEmpty()) {
                viewModel.onEvent(
                    CharacterSkillsAndTalentsEvent.InitSkillsList(
                        from = SpeciesOrCareer.CAREER,
                        speciesName = character.species,
                        careerPathName = character.careerPath
                    )
                )
            }
            if (state.careerTalentsList.isEmpty()) {
                viewModel.onEvent(
                    CharacterSkillsAndTalentsEvent.InitTalentsList(
                        from = SpeciesOrCareer.CAREER,
                        speciesName = character.species,
                        careerPathName = character.careerPath
                    )
                )
            }
        }
    }

    val currentSkills = when (state.speciesOrCareer) {
        SpeciesOrCareer.SPECIES -> state.skillList.getOrNull(0) ?: emptyList()
        SpeciesOrCareer.CAREER -> state.skillList.getOrNull(1) ?: emptyList()
    }

    fun handleSaveSkillsAndTalents(
        state: CharacterSkillsAndTalentsState,
        character: CharacterItem
    ) {
        fun toSkillRow(skill: SkillItem, bonus: Int): List<String> {
            return listOf(
                skill.name,
                skill.attribute.orEmpty(),
                bonus.toString(),
                getAttributeValue(character, skill.attribute.orEmpty()).toString()
            )
        }

        // 1. Pobierz species skille jako mapę (zsumujemy później)
        val speciesMap =
            (state.selectedSkills3.map { it to 3 } + state.selectedSkills5.map { it to 5 })
                .groupBy({ it.first.name }, { it })
                .mapValues { it.value.first() } // zostaje SkillItem + bonus

        // 2. Career skille jako mapa: nazwa → SkillItem + bonus
        val flatCareerSkills = state.careerSkillsList.flatten()
        val careerMap =
            state.careerSkillPoints.filterValues { it > 0 }.mapNotNull { (name, bonus) ->
                flatCareerSkills.find { it.name == name }?.let { it to bonus }
            }.groupBy({ it.first.name }, { it })
                .mapValues { it.value.first() }

        // 3. Scal species i career
        val allSkillsMap = (speciesMap.keys + careerMap.keys).associateWith { skillName ->
            val speciesEntry = speciesMap[skillName]
            val careerEntry = careerMap[skillName]

            val skill = speciesEntry?.first ?: careerEntry?.first!!
            val speciesBonus = speciesEntry?.second ?: 0
            val careerBonus = careerEntry?.second ?: 0
            val totalBonus = speciesBonus + careerBonus

            skill to totalBonus
        }

        val careerBasicSkills = mutableListOf<List<String>>()
        val careerAdvancedSkills = mutableListOf<List<String>>()

        allSkillsMap.values.forEach { (skill, totalBonus) ->
            if (skill.isBasic == true)
                careerBasicSkills.add(toSkillRow(skill, totalBonus))
            else
                careerAdvancedSkills.add(toSkillRow(skill, totalBonus))
        }

        // 4. Talenty
        val allTalents = state.selectedSpeciesTalents + state.selectedCareerTalents

        val talents = allTalents.map {
            listOf(it.name, it.source.orEmpty(), it.page?.toString() ?: "")
        }

        // 5. Zapis
        creatorViewModel.onEvent(
            CharacterCreatorEvent.SetSkillsAndTalents(
                speciesBasicSkills = emptyList(), // już wszystko poszło do career*
                speciesAdvancedSkills = emptyList(), // j.w.
                careerBasicSkills = careerBasicSkills,
                careerAdvancedSkills = careerAdvancedSkills,
                talents = talents
            )
        )
    }

    CharacterSkillsAndTalentsScreen(
        state = state,
        skills = currentSkills,
        speciesLabel = character.species,
        careerLabel = character.careerPath,
        snackbarHostState = snackbarHostState,
        onEvent = { event ->
            when (event) {
                is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> {
                    if (event.isChecked && state.selectedSkills3.size >= 3) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You can only select 3 skills +3!")
                        )
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> {
                    if (event.isChecked && state.selectedSkills5.size >= 3) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You can only select 3 skills +5!")
                        )
                    } else {
                        viewModel.onEvent(event)
                    }
                }

                is CharacterSkillsAndTalentsEvent.OnSaveSkillsAndTalents -> {
                    handleSaveSkillsAndTalents(state, character)
                }

                is CharacterSkillsAndTalentsEvent.OnNextClick -> {
                    if (state.totalAllocatedPoints != 40) {
                        creatorViewModel.onEvent(
                            CharacterCreatorEvent.ShowMessage("You must allocate exactly 40 points to career skills!")
                        )
                        return@CharacterSkillsAndTalentsScreen
                    }
                    handleSaveSkillsAndTalents(state, character)
                    onNextClick()
                }

                is CharacterSkillsAndTalentsEvent.OnBackClick -> onBackClick()

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
    speciesLabel: String,
    careerLabel: String,
    onEvent: (CharacterSkillsAndTalentsEvent) -> Unit,
    snackbarHostState: SnackbarHostState
) {
    MainScaffold(
        title = "Skills and Talents",
        snackbarHost = { CharacterCreatorSnackbarHost(snackbarHostState) },
        onBackClick = { onEvent(CharacterSkillsAndTalentsEvent.OnBackClick) },
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
                    CharacterCreatorTitle("Character Skills and Talents")
                }
                item {
                    SkillsTable(
                        skills = skills,
                        selectedSkills3 = state.selectedSkills3,
                        selectedSkills5 = state.selectedSkills5,
                        speciesOrCareer = state.speciesOrCareer,
                        careerSkillPoints = state.careerSkillPoints,
                        onSkillChecked3 = { skill, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnSkillChecked3(
                                    skill,
                                    isChecked
                                )
                            )
                        },
                        onSkillChecked5 = { skill, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnSkillChecked5(
                                    skill,
                                    isChecked
                                )
                            )
                        },
                        onCareerPointsChanged = { skill, newValue ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnCareerSkillValueChanged(
                                    skill,
                                    newValue
                                )
                            )
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
                        rolledTalents = state.rolledTalents,
                        speciesName = speciesLabel,
                        careerName = careerLabel,
                        isSpeciesMode = state.speciesOrCareer == SpeciesOrCareer.SPECIES,
                        onTalentChecked = { talent, isChecked ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnTalentChecked(
                                    talent,
                                    isChecked
                                )
                            )
                        },
                        onRandomTalentRolled = { groupIndex, talentIndex, rolledName ->
                            onEvent(
                                CharacterSkillsAndTalentsEvent.OnRandomTalentRolled(
                                    groupIndex, talentIndex, rolledName
                                )
                            )
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
    )
}

@Preview
@Composable
fun CharacterSkillsAndTalentsScreenPreview() {
    CharacterSkillsAndTalentsScreen(
        state = CharacterSkillsAndTalentsState(),
        skills = listOf(),
        speciesLabel = "Species",
        careerLabel = "Career",
        onEvent = { },
        snackbarHostState = remember { SnackbarHostState() }
    )
}
