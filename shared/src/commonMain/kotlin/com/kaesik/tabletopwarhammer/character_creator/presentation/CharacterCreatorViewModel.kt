package com.kaesik.tabletopwarhammer.character_creator.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterCreatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterCreatorState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterCreatorEvent) {
    }
}
