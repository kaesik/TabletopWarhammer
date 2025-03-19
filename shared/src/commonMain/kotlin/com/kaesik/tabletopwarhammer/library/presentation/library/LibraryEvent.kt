package com.kaesik.tabletopwarhammer.library.presentation.library

sealed class LibraryEvent {
    data object OnLibrarySelect : LibraryEvent()
}
