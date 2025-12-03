package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource

class AndroidCharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryLocalDataSource: LibraryLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterTrappingsViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryLocalDataSource = libraryLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterTrappingsEvent) {
        viewModel.onEvent(event)
    }
}
