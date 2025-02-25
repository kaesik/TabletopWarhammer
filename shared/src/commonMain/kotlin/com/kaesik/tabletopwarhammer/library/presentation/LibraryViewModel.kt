package com.kaesik.tabletopwarhammer.library.presentation

import kotlinx.coroutines.flow.MutableStateFlow

class LibraryViewModel {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state

    fun onEvent(event: LibraryEvent) {
    }
}
