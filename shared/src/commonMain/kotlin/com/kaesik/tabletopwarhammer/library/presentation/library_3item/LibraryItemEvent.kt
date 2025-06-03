package com.kaesik.tabletopwarhammer.library.presentation.library_3item

import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum

sealed class LibraryItemEvent {
    data class InitItem(val itemId: String, val fromTable: LibraryEnum) : LibraryItemEvent()
}
