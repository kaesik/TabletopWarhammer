package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

data class CharacterSheetState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val character: CharacterItem? = null
)
