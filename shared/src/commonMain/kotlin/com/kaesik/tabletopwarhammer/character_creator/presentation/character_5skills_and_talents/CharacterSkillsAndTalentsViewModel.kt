package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
            is CharacterSkillsAndTalentsEvent.InitSkillsList -> {
                loadSkillsList(
                    speciesName = "Human",
                    careerName = "Noble",
                    careerPathName = "Scion",
                )
            }

            is CharacterSkillsAndTalentsEvent.InitTalentsList -> {
                loadTalentsList(
                    speciesName = "Human",
                    careerName = "Noble",
                    careerPathName = "Scion",
                )
            }

            else -> Unit
        }
    }

    private fun loadSkillsList(
        speciesName: String,
        careerName: String,
        careerPathName: String,
    ) {
        skillsJob?.cancel()
        skillsJob = viewModelScope.launch {
            val skillList = characterCreatorClient.getSkills(
                speciesName = speciesName,
                careerName = careerName,
                careerPathName = careerPathName,
            )
            _state.value = state.value.copy(
                skillList = skillList,
            )
        }
    }

    private fun loadTalentsList(
        speciesName: String,
        careerName: String,
        careerPathName: String,
    ) {
        talentsJob?.cancel()
        talentsJob = viewModelScope.launch {
            val talentList = characterCreatorClient.getTalents(
                speciesName = speciesName,
                careerName = careerName,
                careerPathName = careerPathName,
            )
            _state.value = state.value.copy(
                talentList = talentList,
            )
        }
    }
}
