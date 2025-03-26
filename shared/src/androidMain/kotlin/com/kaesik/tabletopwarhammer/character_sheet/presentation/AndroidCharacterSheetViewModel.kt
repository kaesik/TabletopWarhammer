package com.kaesik.tabletopwarhammer.character_sheet.presentation

import androidx.lifecycle.ViewModel

class AndroidCharacterSheetViewModel(

) : ViewModel() {

    private val viewModel by lazy {
        CharacterSheetViewModel(

        )
    }

    val state = viewModel.state

    fun onEvent(event: CharacterSheetEvent) {
        viewModel.onEvent(event)
    }
}
