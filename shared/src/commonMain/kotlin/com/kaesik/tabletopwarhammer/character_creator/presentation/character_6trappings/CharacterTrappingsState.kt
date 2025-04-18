package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem

data class CharacterTrappingsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val trappingList: List<ItemItem> = emptyList(),
)
