package com.kaesik.tabletopwarhammer.character_sheet.presentation

import kotlinx.coroutines.flow.MutableStateFlow

class CharacterSheetViewModel {
    private val _state = MutableStateFlow(CharacterSheetState())
    val state = _state

    fun onEvent(event: CharacterSheetEvent) {
    }
}
