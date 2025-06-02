package com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterFinalState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
