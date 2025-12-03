package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource

class AndroidCharacterClassAndCareerViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryLocalDataSource: LibraryLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterClassAndCareerViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryLocalDataSource = libraryLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterClassAndCareerEvent) {
        viewModel.onEvent(event)
    }
}
