package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

sealed class CharacterSheetEvent {
    data class ShowMessage(val message: String) : CharacterSheetEvent()
    data object ClearMessage : CharacterSheetEvent()

    data class LoadCharacterById(val id: Int) : CharacterSheetEvent()
    data class SaveCharacter(val character: CharacterItem) : CharacterSheetEvent()
}
