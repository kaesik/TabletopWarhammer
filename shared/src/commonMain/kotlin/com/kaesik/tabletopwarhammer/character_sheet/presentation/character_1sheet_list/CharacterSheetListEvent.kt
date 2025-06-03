package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

sealed class CharacterSheetListEvent {
    data object LoadCharacters : CharacterSheetListEvent()
    data class OnCharacterClick(val character: CharacterItem) : CharacterSheetListEvent()
    data class OnDeleteCharacter(val character: CharacterItem) : CharacterSheetListEvent()
}
