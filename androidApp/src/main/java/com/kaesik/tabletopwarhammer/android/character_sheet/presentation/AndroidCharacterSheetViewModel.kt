package com.kaesik.tabletopwarhammer.android.character_sheet.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetViewModel

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
