package com.kaesik.tabletopwarhammer.main.presentation.about

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class AboutState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,
)
