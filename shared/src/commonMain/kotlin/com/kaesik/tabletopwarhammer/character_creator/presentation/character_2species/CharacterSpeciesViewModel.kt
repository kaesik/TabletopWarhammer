package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterSpeciesViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterSpeciesState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterSpeciesEvent) {
    }
}
