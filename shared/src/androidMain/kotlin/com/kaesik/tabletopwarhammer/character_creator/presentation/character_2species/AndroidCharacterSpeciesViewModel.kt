package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient

class AndroidCharacterSpeciesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSpeciesViewModel(
            characterCreatorClient = characterCreatorClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSpeciesEvent) {
        viewModel.onEvent(event)
    }
}
