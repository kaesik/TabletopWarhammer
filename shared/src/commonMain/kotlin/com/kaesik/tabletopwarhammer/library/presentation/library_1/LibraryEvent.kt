package com.kaesik.tabletopwarhammer.library.presentation.library_1

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum

sealed class LibraryEvent {
    data class OnLibraryListSelect(val fromTable: LibraryEnum) : LibraryEvent()
}
