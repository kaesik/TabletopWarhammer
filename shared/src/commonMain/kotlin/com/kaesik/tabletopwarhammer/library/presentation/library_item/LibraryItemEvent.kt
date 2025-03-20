package com.kaesik.tabletopwarhammer.library.presentation.library_item

sealed class LibraryItemEvent {
    data object OnFavoriteClick : LibraryItemEvent()
    data object OnBackClick : LibraryItemEvent()
}
