package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterSpeciesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterSpeciesState())
    val state = _state.asStateFlow()

    private var characterSpeciesJob: Job? = null

    fun onEvent(event: CharacterSpeciesEvent) {
        when (event) {
            is CharacterSpeciesEvent.InitSpeciesList -> {
                loadSpeciesList()
            }

            is CharacterSpeciesEvent.OnSpeciesSelect -> {
                val selected = _state.value.speciesList.find { it.id == event.id }
                _state.value = _state.value.copy(selectedSpecies = selected)
            }

            is CharacterSpeciesEvent.RollRandomSpecies -> {
                val list = _state.value.speciesList
                if (list.isNotEmpty()) {
                    val random = list.random()
                    _state.value = _state.value.copy(
                        selectedSpecies = random,
                        hasRolledSpecies = true
                    )
                }
            }

            else -> Unit
        }
    }

    private fun loadSpeciesList() {
        characterSpeciesJob?.cancel()
        characterSpeciesJob = viewModelScope.launch {
            val speciesList = characterCreatorClient.getSpecies()
            _state.value = state.value.copy(
                speciesList = speciesList,
            )
        }
    }
}
