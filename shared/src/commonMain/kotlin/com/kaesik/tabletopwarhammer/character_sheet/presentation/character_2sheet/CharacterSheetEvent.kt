package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

sealed class CharacterSheetEvent {
    data class ShowMessage(val message: String) : CharacterSheetEvent()
    data object ClearMessage : CharacterSheetEvent()

    data class LoadCharacterById(val id: Int) : CharacterSheetEvent()
}
