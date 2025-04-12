package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

import androidx.lifecycle.ViewModel

class AndroidCharacterDetailsViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterDetailsViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterDetailsEvent) {
        viewModel.onEvent(event)
    }
}
