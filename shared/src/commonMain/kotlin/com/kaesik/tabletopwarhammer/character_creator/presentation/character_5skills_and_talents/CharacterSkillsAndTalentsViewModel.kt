package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.mapSpecializedSkills
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSkillsAndTalentsState())
    val state = _state.asStateFlow()

    private var skillsJob: Job? = null
    private var talentsJob: Job? = null

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        when (event) {
            is CharacterSkillsAndTalentsEvent.InitSkillsList -> loadSkillsList(
                event.from, event.speciesName, event.careerPathName
            )

            is CharacterSkillsAndTalentsEvent.InitTalentsList -> loadTalentsList(
                event.from, event.speciesName, event.careerPathName
            )

            is CharacterSkillsAndTalentsEvent.OnSkillChecked -> updateSelectedSkills(
                event.skill,
                event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> updateSelectedSkills3(
                event.skill,
                event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> updateSelectedSkills5(
                event.skill,
                event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnTalentChecked -> handleTalentChecked(
                event.talent,
                event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnRandomTalentRolled -> {
                _state.update { current ->
                    val updatedMap = current.rolledTalents + (Pair(
                        event.groupIndex,
                        event.talentIndex
                    ) to event.rolledName)
                    current.copy(rolledTalents = updatedMap)
                }
            }

            is CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick -> toggleSpeciesOrCareer()

            is CharacterSkillsAndTalentsEvent.OnCareerSkillValueChanged -> {
                val newMap = _state.value.careerSkillPoints.toMutableMap()
                newMap[event.skill.name] = event.newValue
                val total = newMap.values.sum()
                if (event.newValue in 0..10 && total <= 40) {
                    _state.update {
                        it.copy(careerSkillPoints = newMap, totalAllocatedPoints = total)
                    }
                }
            }

            else -> Unit
        }
    }

    private fun handleTalentChecked(talent: TalentItem, isChecked: Boolean) {
        _state.update { current ->
            val baseName = talent.name.substringBefore(" or ").trim()
            val updatedTalents = if (isChecked) {
                current.selectedTalents.filterNot { it.name.startsWith(baseName) } + talent
            } else {
                current.selectedTalents - talent
            }
            current.copy(selectedTalents = updatedTalents)
        }
    }

    private fun updateSelectedSkills(skill: SkillItem, isChecked: Boolean) {
        _state.update {
            val updated = if (isChecked) it.selectedSkills + skill else it.selectedSkills - skill
            it.copy(selectedSkills = updated)
        }
    }

    private fun toggleSpeciesOrCareer() {
        _state.update { current ->
            val updatedState = when (current.speciesOrCareer) {
                SpeciesOrCareer.SPECIES -> current.copy(
                    selectedSpeciesTalents = current.selectedTalents
                )

                SpeciesOrCareer.CAREER -> current.copy(
                    selectedCareerTalents = current.selectedTalents
                )
            }

            updatedState.copy(
                speciesOrCareer = if (current.speciesOrCareer == SpeciesOrCareer.SPECIES)
                    SpeciesOrCareer.CAREER else SpeciesOrCareer.SPECIES,
                selectedTalents = if (current.speciesOrCareer == SpeciesOrCareer.SPECIES)
                    current.selectedCareerTalents else current.selectedSpeciesTalents
            )
        }
    }

    private fun loadSkillsList(
        from: SpeciesOrCareer,
        speciesName: String,
        careerPathName: String,
    ) {
        skillsJob?.cancel()
        skillsJob = viewModelScope.launch {
            val skillsList = if (true) {
                libraryDataSource.getFilteredSkills(
                    speciesName = speciesName,
                    careerPathName = careerPathName
                )
            } else {
                characterCreatorClient.getSkills(
                    speciesName = speciesName,
                    careerPathName = careerPathName
                )
            }
            val specializedSkillsList = mapSpecializedSkills(skillsList)

            _state.update {
                when (from) {
                    SpeciesOrCareer.SPECIES -> it.copy(speciesSkillsList = specializedSkillsList)
                    SpeciesOrCareer.CAREER -> it.copy(careerSkillsList = specializedSkillsList)
                }.copy(skillList = specializedSkillsList)
            }
        }
    }

    private fun loadTalentsList(
        from: SpeciesOrCareer,
        speciesName: String,
        careerPathName: String,
    ) {
        talentsJob?.cancel()
        talentsJob = viewModelScope.launch {

            val talentList = if (true) {
                libraryDataSource.getFilteredTalents(speciesName, careerPathName)
            } else {
                characterCreatorClient.getTalents(speciesName, careerPathName)
            }

            val speciesTalentGroups = talentList.getOrNull(0) ?: emptyList()
            val careerTalentGroups = talentList.getOrNull(1) ?: emptyList()

            fun processTalentGroups(talentGroups: List<List<TalentItem>>): List<List<TalentItem>> {
                return talentGroups.map { group ->
                    if (group.size == 1 && " or " in group[0].name) {
                        // Rozbij tylko jeśli jest pojedynczy element z "or" w nazwie
                        group[0].name.split(" or ").map { option ->
                            group[0].copy(name = option.trim())
                        }
                    } else group // Jeśli więcej niż 1 talent lub nie ma "or", zostaw jak jest
                }
            }

            val processedSpeciesTalents = processTalentGroups(speciesTalentGroups)
            val processedCareerTalents = processTalentGroups(careerTalentGroups)

            val autoSelectedTalents = (speciesTalentGroups + careerTalentGroups).flatMap { group ->
                when {
                    group.all { it.name == "Random Talent" } -> emptyList()
                    group.size > 1 -> listOf(group.first())
                    else -> group
                }
            }

            _state.update { current ->
                current.copy(
                    speciesTalentsList = processedSpeciesTalents,
                    careerTalentsList = processedCareerTalents,
                    talentList = processedSpeciesTalents + processedCareerTalents,
                    selectedTalents = autoSelectedTalents
                )
            }
        }
    }

    private fun updateSelectedSkills3(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            val removedFrom5 = current.selectedSkills5.filterNot { it.name == skill.name }
            val updated3 = if (isChecked) current.selectedSkills3 + skill
            else current.selectedSkills3.filterNot { it.name == skill.name }
            current.copy(selectedSkills3 = updated3, selectedSkills5 = removedFrom5)
        }
    }

    private fun updateSelectedSkills5(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            val removedFrom3 = current.selectedSkills3.filterNot { it.name == skill.name }
            val updated5 = if (isChecked) current.selectedSkills5 + skill
            else current.selectedSkills5.filterNot { it.name == skill.name }
            current.copy(selectedSkills3 = removedFrom3, selectedSkills5 = updated5)
        }
    }
}
