package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel

class AndroidCharacterSkillsAndTalentsViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterSkillsAndTalentsViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        viewModel.onEvent(event)
    }
}
