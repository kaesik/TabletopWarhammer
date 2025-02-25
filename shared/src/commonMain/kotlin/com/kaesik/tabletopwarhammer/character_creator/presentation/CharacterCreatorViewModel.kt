package com.kaesik.tabletopwarhammer.character_creator.presentation

import kotlinx.coroutines.flow.MutableStateFlow

class CharacterCreatorViewModel {
    private val _state = MutableStateFlow(CharacterCreatorState())
    val state = _state

    fun onEvent(event: CharacterCreatorEvent) {
    }
}
