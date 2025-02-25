package com.kaesik.tabletopwarhammer.library.presentation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LibraryViewModel {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.LoadLibrary -> loadLibrary()
        }
    }

    private fun loadLibrary() {
        _state.update { it.copy(
                error = null
            )
        }
    }
}
