package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient

class AndroidCharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val viewModel by lazy {
        CharacterTrappingsViewModel(
            characterCreatorClient = characterCreatorClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterTrappingsEvent) {
        viewModel.onEvent(event)
    }
}
