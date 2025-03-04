package com.kaesik.tabletopwarhammer.library.presentation.library_list

sealed class LibraryListEvent {
    data class LoadItem(val name: String) : LibraryListEvent()
}
