package com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question

data class CharacterTenQuestionsState(
    val error: String? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
