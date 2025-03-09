package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryListState (
    val error: LibraryError? = null,
    val list : List<LibraryItem> = emptyList(),
    val id: String? = null,
    val result: LibraryItem? = null,
    val isLoading: Boolean = false
)
