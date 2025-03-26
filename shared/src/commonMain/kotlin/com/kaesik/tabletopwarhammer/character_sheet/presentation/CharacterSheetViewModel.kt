package com.kaesik.tabletopwarhammer.character_sheet.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterSheetViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterSheetState())
    val state = _state.asStateFlow()

    fun onEvent(event: CharacterSheetEvent) {
    }
}
