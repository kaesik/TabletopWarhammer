package com.kaesik.tabletopwarhammer.library.presentation.library_list

sealed class LibraryListEvent {
    data class LoadItems(val type: String) : LibraryListEvent()
}
