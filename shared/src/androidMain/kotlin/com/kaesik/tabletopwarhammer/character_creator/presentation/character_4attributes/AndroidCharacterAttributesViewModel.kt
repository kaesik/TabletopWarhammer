package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource

class AndroidCharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterAttributesViewModel(
            characterCreatorClient = characterCreatorClient,
            libraryDataSource = libraryDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterAttributesEvent) {
        viewModel.onEvent(event)
    }

    fun restoreRolledAttributes(rolled: List<Int>, total: List<Int>) {
        viewModel.restoreRolledAttributes(rolled, total)
    }
}
