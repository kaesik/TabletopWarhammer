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

    private var characterSkillsAndTalentsJob: Job? = null

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        when (event) {
            is CharacterSkillsAndTalentsEvent.InitSkillsList -> {
                loadSkillsList()
            }

            is CharacterSkillsAndTalentsEvent.InitTalentsList -> {
                loadTalentsList()
            }

            else -> Unit
        }
    }

    private fun loadSkillsList() {
        characterSkillsAndTalentsJob?.cancel()
        characterSkillsAndTalentsJob = viewModelScope.launch {
            val skillList = characterCreatorClient.getSkills()
            _state.value = state.value.copy(
                skillList = skillList,
            )
        }
    }

    private fun loadTalentsList() {
        characterSkillsAndTalentsJob?.cancel()
        characterSkillsAndTalentsJob = viewModelScope.launch {
            val talentList = characterCreatorClient.getTalents()
            _state.value = state.value.copy(
                talentList = talentList,
            )
        }
    }
}
