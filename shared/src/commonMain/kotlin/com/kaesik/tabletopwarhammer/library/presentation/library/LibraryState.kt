package com.kaesik.tabletopwarhammer.library.presentation.library

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError

data class LibraryState(
    val error: LibraryError? = null,
    val fromTable: String? = null,
    val result: List<Any>? = emptyList(),
    val isLoading: Boolean = false,
)
