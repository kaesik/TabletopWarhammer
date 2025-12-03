package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource

class AndroidCharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryLocalDataSource: LibraryLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSkillsAndTalentsViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryLocalDataSource = libraryLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        viewModel.onEvent(event)
    }
}
