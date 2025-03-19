package com.kaesik.tabletopwarhammer.library.presentation.library_item

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryError
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class LibraryItemState (
    val error: LibraryError? = null,
    val isLoading: Boolean = false,

    val fromList: List<LibraryItem> = emptyList(),
    val result : LibraryItem? = null,
)
