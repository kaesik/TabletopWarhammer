package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterTrappingsViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterTrappingsState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterTrappingsEvent) {
    }
}
