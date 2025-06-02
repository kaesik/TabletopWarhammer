package com.kaesik.tabletopwarhammer.character_creator.presentation.character_9advancement

data class CharacterAdvancementState(
    val error: String? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
