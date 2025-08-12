package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource

class AndroidCharacterTrappingsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterTrappingsViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryDataSource = libraryDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterTrappingsEvent) {
        viewModel.onEvent(event)
    }
}
