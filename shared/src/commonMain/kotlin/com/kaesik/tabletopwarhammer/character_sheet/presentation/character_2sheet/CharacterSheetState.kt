package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterSheetState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val character: CharacterItem? = null
)
