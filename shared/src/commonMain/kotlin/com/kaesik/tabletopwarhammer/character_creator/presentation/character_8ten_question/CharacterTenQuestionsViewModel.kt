package com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterTenQuestionsViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterTenQuestionsState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterTenQuestionsEvent) {
    }
}
