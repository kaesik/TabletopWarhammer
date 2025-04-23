package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

data class CharacterSkillsAndTalentsState(
    val error: String? = null,
    val isLoading: Boolean = false,

    val skillList: List<List<String>> = emptyList(),
    val talentList: List<List<String>> = emptyList(),
)
