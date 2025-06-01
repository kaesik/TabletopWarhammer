package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource

class AndroidCharacterSheetListViewModel(
    private val characterDataSource: CharacterDataSource,
) : ViewModel() {

    private val viewModel by lazy {
        CharacterSheetListViewModel(
            characterDataSource = characterDataSource
        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSheetListEvent) {
        viewModel.onEvent(event)
    }
}
