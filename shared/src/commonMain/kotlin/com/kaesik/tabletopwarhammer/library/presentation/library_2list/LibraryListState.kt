package com.kaesik.tabletopwarhammer.library.presentation.library_2list

import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryListState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val libraryList: List<LibraryItem> = emptyList()
)
