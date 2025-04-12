package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel

class AndroidCharacterAttributesViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterAttributesViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterAttributesEvent) {
        viewModel.onEvent(event)
    }
}
