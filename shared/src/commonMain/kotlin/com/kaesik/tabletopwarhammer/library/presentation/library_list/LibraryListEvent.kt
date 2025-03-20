package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

sealed class LibraryListEvent {
    data class OnLibraryItemSelect(val libraryItem: LibraryItem) : LibraryListEvent()
}
