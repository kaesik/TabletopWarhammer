package com.kaesik.tabletopwarhammer.library.presentation.library

sealed class LibraryEvent {
    data class OnLibrarySelect(val fromTable: String) : LibraryEvent()
}
