package com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question

import androidx.lifecycle.ViewModel

class AndroidCharacterTenQuestionsViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterTenQuestionsViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterTenQuestionsEvent) {
        viewModel.onEvent(event)
    }
}
