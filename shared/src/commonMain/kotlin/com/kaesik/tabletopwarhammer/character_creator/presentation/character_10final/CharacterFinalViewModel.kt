package com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterFinalViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterFinalState())
    val state = _state.asStateFlow()

    private var finalJob: Job? = null

    fun onEvent(event: CharacterFinalEvent) {
        when (event) {
            is CharacterFinalEvent.OnSaveClick -> saveCharacter()

            else -> Unit
        }
    }

    private fun saveCharacter() {}
}
