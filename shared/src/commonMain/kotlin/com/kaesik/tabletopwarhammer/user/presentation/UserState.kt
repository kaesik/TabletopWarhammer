package com.kaesik.tabletopwarhammer.user.presentation

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class UserState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
