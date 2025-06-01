package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

sealed class CharacterSheetEvent {
    data class LoadCharacterById(val id: Int) : CharacterSheetEvent()
    data object OnBackClick : CharacterSheetEvent()
}
