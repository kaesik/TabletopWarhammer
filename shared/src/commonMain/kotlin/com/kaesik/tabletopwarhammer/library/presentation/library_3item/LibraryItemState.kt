package com.kaesik.tabletopwarhammer.library.presentation.library_3item

import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryItemState(
    val error: LibraryError? = null,
    val isLoading: Boolean = false,

    val libraryItem: LibraryItem? = null,
    val isFavorite: Boolean = false,
)
