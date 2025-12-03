package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterLocalDataSource

class AndroidCharacterSheetViewModel(
    private val characterLocalDataSource: CharacterLocalDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSheetViewModel(
            characterLocalDataSource = characterLocalDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSheetEvent) {
        viewModel.onEvent(event)
    }
}
