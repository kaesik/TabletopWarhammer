package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

data class CharacterClassAndCareerState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val classList: List<String> = emptyList(),
    val careerList: List<String> = emptyList(),
)
