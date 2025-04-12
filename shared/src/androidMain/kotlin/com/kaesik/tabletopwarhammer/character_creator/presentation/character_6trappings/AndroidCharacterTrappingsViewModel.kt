package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel

class AndroidCharacterTrappingsViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterTrappingsViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterTrappingsEvent) {
        viewModel.onEvent(event)
    }
}
