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
    var onAllRolled: (() -> Unit)?
        get() = viewModel.onAllRolled
        set(value) {
            viewModel.onAllRolled = value
        }

    fun onEvent(event: CharacterAttributesEvent) {
        viewModel.onEvent(event)
    }
}
