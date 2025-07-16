package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient

class AndroidCharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val viewModel by lazy {
        CharacterAttributesViewModel(
            characterCreatorClient = characterCreatorClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterAttributesEvent) {
        viewModel.onEvent(event)
    }

    fun restoreRolledAttributes(rolled: List<Int>, total: List<Int>) {
        viewModel.restoreRolledAttributes(rolled, total)
    }
}
