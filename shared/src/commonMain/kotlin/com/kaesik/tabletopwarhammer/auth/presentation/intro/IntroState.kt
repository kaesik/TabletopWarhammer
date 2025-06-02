package com.kaesik.tabletopwarhammer.auth.presentation.intro

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class IntroState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
