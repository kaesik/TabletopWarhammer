package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource

class AndroidCharacterSheetViewModel(
    private val characterDataSource: CharacterDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSheetViewModel(
            characterDataSource = characterDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSheetEvent) {
        viewModel.onEvent(event)
    }
}
