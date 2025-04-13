package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterTrappingsState())
    val state = _state.asStateFlow()

    private var characterTrappingsJob: Job? = null

    fun onEvent(event: CharacterTrappingsEvent) {
        when (event) {
            is CharacterTrappingsEvent.InitTrappingsList -> {
                loadTrappingsList()
            }

            else -> Unit
        }
    }

    private fun loadTrappingsList() {
        characterTrappingsJob?.cancel()
        characterTrappingsJob = viewModelScope.launch {
            val trappingList = characterCreatorClient.getTrappings()
            _state.value = state.value.copy(
                trappingList = trappingList,
            )
        }
    }
}
