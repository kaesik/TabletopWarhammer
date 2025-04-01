package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum

sealed class LibraryListEvent {
    data class InitList(val fromTable: LibraryEnum) : LibraryListEvent()
    data class OnLibraryItemSelect(val id: String) : LibraryListEvent()
}
