package com.kaesik.tabletopwarhammer.library.presentation

sealed class LibraryEvent {
    data class LoadLibrary(val library: String) : LibraryEvent()
}
