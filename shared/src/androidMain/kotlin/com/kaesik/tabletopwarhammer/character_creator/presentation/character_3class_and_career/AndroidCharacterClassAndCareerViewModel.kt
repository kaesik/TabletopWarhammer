package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient

class AndroidCharacterClassAndCareerViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val viewModel by lazy {
        CharacterClassAndCareerViewModel(
            characterCreatorClient = characterCreatorClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterClassAndCareerEvent) {
        viewModel.onEvent(event)
    }
}
