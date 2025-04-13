package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterClassAndCareerViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterClassAndCareerState())
    val state = _state.asStateFlow()

    private var characterClassAndCareerJob: Job? = null

    fun onEvent(event: CharacterClassAndCareerEvent) {
        when (event) {
            is CharacterClassAndCareerEvent.InitCareerList -> {
                loadCareersList()
            }

            is CharacterClassAndCareerEvent.InitClassList -> {
                loadClassesList()
            }

            else -> Unit
        }
    }

    private fun loadClassesList() {
        characterClassAndCareerJob?.cancel()
        characterClassAndCareerJob = viewModelScope.launch {
            val classList = characterCreatorClient.getClasses()
            _state.value = state.value.copy(
                classList = classList,
            )
        }
    }


    private fun loadCareersList() {
        characterClassAndCareerJob?.cancel()
        characterClassAndCareerJob = viewModelScope.launch {
            val careerList = characterCreatorClient.getCareers()
            _state.value = state.value.copy(
                careerList = careerList,
            )
        }
    }
}
