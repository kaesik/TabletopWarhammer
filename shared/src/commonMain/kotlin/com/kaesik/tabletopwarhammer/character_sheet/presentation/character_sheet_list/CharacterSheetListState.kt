package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet_list

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

data class CharacterSheetListState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val characters: List<CharacterItem> = emptyList()
)
