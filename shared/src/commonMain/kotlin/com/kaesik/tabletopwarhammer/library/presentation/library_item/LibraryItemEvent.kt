package com.kaesik.tabletopwarhammer.library.presentation.library_item

import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum

sealed class LibraryItemEvent {
    data class InitItem(val itemId: String, val fromTable: LibraryEnum) : LibraryItemEvent()
    data object OnFavoriteClick : LibraryItemEvent()
    data object OnBackClick : LibraryItemEvent()
}
