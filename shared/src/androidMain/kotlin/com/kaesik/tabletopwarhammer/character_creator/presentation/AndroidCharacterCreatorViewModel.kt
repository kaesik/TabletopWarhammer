package com.kaesik.tabletopwarhammer.character_creator.presentation

import androidx.lifecycle.ViewModel

class AndroidCharacterCreatorViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterCreatorViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterCreatorEvent) {
        viewModel.onEvent(event)
    }
}
