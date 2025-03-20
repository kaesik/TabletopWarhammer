package com.kaesik.tabletopwarhammer.library.presentation.library

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

sealed class LibraryEvent {
    data class OnLibrarySelect(val libraryList: List<LibraryItem>) : LibraryEvent()
}
