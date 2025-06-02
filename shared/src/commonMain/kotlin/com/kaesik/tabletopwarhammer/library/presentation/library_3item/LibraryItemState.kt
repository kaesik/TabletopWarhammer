package com.kaesik.tabletopwarhammer.library.presentation.library_3item

import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryItemState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val libraryItem: LibraryItem? = null,
    val isFavorite: Boolean = false,
)
