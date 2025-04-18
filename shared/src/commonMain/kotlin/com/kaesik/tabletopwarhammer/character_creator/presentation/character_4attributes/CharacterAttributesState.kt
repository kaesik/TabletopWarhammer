package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem

data class CharacterAttributesState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val attributeList: List<AttributeItem> = emptyList(),
)
