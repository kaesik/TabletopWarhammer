package com.kaesik.tabletopwarhammer.library.presentation.library

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryState(
    val error: LibraryError? = null,
    val fromTable: String? = null,
    val result: List<LibraryItem>? = emptyList(),
    val isLoading: Boolean = false,
)
