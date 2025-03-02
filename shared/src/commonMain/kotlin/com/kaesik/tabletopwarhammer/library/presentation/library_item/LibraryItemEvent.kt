package com.kaesik.tabletopwarhammer.library.presentation.library_item

sealed class LibraryItemEvent {
    data class LoadItem(val name: String) : LibraryItemEvent()
}
