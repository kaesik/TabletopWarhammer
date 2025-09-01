package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterTrappingsState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val classTrappings: List<ItemItem> = emptyList(),
    val careerTrappings: List<ItemItem> = emptyList(),

    val wealth: List<Int> = emptyList(),
)
