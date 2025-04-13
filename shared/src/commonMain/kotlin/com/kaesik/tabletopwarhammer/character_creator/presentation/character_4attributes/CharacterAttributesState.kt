package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

data class CharacterAttributesState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val attributeList: List<String> = emptyList(),
)
