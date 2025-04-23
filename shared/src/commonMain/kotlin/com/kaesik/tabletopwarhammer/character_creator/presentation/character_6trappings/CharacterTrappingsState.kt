package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

data class CharacterTrappingsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val trappingList: List<List<String>> = emptyList(),
)
