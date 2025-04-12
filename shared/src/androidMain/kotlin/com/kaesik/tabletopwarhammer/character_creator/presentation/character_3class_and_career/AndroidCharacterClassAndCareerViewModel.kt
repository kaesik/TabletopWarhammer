package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel

class AndroidCharacterClassAndCareerViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterClassAndCareerViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterClassAndCareerEvent) {
        viewModel.onEvent(event)
    }
}
