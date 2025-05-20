package com.kaesik.tabletopwarhammer.character_sheet.domain

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

interface CharacterSheetClient {
    suspend fun getAllCharacters(): List<CharacterItem>
}
