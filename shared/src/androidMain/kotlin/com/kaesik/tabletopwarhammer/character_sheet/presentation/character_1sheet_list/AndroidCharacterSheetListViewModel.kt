package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterLocalDataSource

class AndroidCharacterSheetListViewModel(
    private val characterLocalDataSource: CharacterLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSheetListViewModel(
            characterLocalDataSource = characterLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSheetListEvent) {
        viewModel.onEvent(event)
    }
}
