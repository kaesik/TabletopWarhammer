package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterSheetListState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val characters: List<CharacterItem> = emptyList()
)
