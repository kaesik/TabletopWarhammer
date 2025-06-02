package com.kaesik.tabletopwarhammer.menu.presentation

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class MenuState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val isLoggedOut: Boolean = false,
)
