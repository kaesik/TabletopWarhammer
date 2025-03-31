package com.kaesik.tabletopwarhammer.library.presentation.library

sealed class LibraryEvent {
    data class OnLibraryListSelect(val fromTable: String) : LibraryEvent()
}
