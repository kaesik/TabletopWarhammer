package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient

class AndroidCharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSkillsAndTalentsViewModel(
            characterCreatorClient = characterCreatorClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        viewModel.onEvent(event)
    }
}
