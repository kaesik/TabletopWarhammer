package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorViewModel

class AndroidCharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val characterCreatorViewModel: CharacterCreatorViewModel,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSkillsAndTalentsViewModel(
            characterCreatorClient = characterCreatorClient,
            characterCreatorViewModel = characterCreatorViewModel,
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        viewModel.onEvent(event)
    }
}
