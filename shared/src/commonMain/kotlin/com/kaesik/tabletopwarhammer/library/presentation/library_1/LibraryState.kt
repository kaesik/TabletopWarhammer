package com.kaesik.tabletopwarhammer.library.presentation.library_1

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class LibraryState(
    val error: DataError? = null,
    val isLoading: Boolean = false,
)
