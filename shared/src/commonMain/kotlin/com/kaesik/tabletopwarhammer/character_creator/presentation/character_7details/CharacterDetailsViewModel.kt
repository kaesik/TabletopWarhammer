package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterDetailsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterDetailsState())
    val state = _state.asStateFlow()

    private var characterDetailsJob: Job? = null

    fun onEvent(event: CharacterDetailsEvent) {
    }
}
