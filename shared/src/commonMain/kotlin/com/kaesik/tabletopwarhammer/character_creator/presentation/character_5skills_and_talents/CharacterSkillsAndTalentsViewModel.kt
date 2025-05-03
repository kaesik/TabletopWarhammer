package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
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
            _state.update {
                when (from) {
                    SpeciesOrCareer.SPECIES -> it.copy(speciesSkillsList = result)
                    SpeciesOrCareer.CAREER -> it.copy(careerSkillsList = result)
                }.copy(skillList = result)
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
}
