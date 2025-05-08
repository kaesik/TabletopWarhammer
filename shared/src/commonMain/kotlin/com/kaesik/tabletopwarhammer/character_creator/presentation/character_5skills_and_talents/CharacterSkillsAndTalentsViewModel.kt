package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.mapSpecializedSkills
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
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
                event.skill, event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> updateSelectedSkills3(
                event.skill, event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> updateSelectedSkills5(
                event.skill, event.isChecked
            )

            is CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick -> toggleSpeciesOrCareer()

            else -> Unit
        }
    }

    private fun updateSelectedSkills(skill: SkillItem, isChecked: Boolean) {
        _state.update {
            val updated = if (isChecked) it.selectedSkills + skill else it.selectedSkills - skill
            it.copy(selectedSkills = updated)
        }
    }

    private fun toggleSpeciesOrCareer() {
        _state.update {
            it.copy(
                speciesOrCareer = if (it.speciesOrCareer == SpeciesOrCareer.SPECIES)
                    SpeciesOrCareer.CAREER
                else
                    SpeciesOrCareer.SPECIES
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
            val result = characterCreatorClient.getSkills(
                speciesName = speciesName,
                careerPathName = careerPathName
            )
            val mapped = mapSpecializedSkills(result)
            _state.update {
                when (from) {
                    SpeciesOrCareer.SPECIES -> it.copy(speciesSkillsList = mapped)
                    SpeciesOrCareer.CAREER -> it.copy(careerSkillsList = mapped)
                }.copy(skillList = mapped)
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
            val result = characterCreatorClient.getTalents(speciesName, careerPathName)
            _state.update {
                when (from) {
                    SpeciesOrCareer.SPECIES -> it.copy(speciesTalentsList = result)
                    SpeciesOrCareer.CAREER -> it.copy(careerTalentsList = result)
                }.copy(talentList = result)
            }
        }
    }

    private fun updateSelectedSkills3(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            val removedFrom5 = current.selectedSkills5.filterNot { it.name == skill.name }
            val updated3 = if (isChecked) current.selectedSkills3 + skill
            else current.selectedSkills3.filterNot { it.name == skill.name }
            current.copy(
                selectedSkills3 = updated3,
                selectedSkills5 = removedFrom5
            )
        }
    }

    private fun updateSelectedSkills5(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            val removedFrom3 = current.selectedSkills3.filterNot { it.name == skill.name }
            val updated5 = if (isChecked) current.selectedSkills5 + skill
            else current.selectedSkills5.filterNot { it.name == skill.name }
            current.copy(
                selectedSkills3 = removedFrom3,
                selectedSkills5 = updated5
            )
        }
    }
}
