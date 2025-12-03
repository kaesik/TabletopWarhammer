package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource

class AndroidCharacterSpeciesViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryLocalDataSource: LibraryLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSpeciesViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryLocalDataSource = libraryLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSpeciesEvent) {
        viewModel.onEvent(event)
    }
}
