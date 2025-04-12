package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterDetailsEvent) {
    }
}
