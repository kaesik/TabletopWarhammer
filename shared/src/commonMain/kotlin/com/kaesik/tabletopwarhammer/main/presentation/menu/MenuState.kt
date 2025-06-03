package com.kaesik.tabletopwarhammer.main.presentation.menu

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class MenuState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
