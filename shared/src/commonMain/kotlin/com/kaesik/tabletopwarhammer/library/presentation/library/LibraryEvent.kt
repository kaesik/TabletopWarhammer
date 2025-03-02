package com.kaesik.tabletopwarhammer.library.presentation.library

sealed class LibraryEvent {
    data class LoadLibrary(val library: String) : LibraryEvent()
}
