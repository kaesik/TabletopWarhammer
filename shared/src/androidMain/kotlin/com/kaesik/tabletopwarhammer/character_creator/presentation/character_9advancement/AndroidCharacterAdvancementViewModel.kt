package com.kaesik.tabletopwarhammer.character_creator.presentation.character_9advancement

import androidx.lifecycle.ViewModel

class AndroidCharacterAdvancementViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterAdvancementViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterAdvancementEvent) {
        viewModel.onEvent(event)
    }
}
