package com.kaesik.tabletopwarhammer.character_creator.presentation.character_9advancement

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterAdvancementViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterAdvancementState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterAdvancementEvent) {
    }
}
