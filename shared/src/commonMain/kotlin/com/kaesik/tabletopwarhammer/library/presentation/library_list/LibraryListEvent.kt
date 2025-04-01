package com.kaesik.tabletopwarhammer.library.presentation.library_list

sealed class LibraryListEvent {
    data class InitList(val fromTable: String) : LibraryListEvent()
    data class OnLibraryItemSelect(val id: String) : LibraryListEvent()
}
