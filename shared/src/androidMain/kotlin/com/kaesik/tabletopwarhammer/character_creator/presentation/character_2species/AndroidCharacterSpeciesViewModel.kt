package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel

class AndroidCharacterSpeciesViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterSpeciesViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSpeciesEvent) {
        viewModel.onEvent(event)
    }
}
