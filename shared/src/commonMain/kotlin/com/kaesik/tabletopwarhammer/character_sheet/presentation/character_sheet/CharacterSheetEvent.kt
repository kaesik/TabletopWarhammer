package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet

sealed class CharacterSheetEvent {
    data class LoadCharacterById(val id: Int) : CharacterSheetEvent()
}
