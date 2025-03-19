package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum

sealed class LibraryListEvent {
    data class LoadLibrary(val fromTable: Enum<LibraryEnum>) : LibraryListEvent()
}
