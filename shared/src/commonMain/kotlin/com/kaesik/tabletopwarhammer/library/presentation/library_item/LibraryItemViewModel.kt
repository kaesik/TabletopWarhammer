package com.kaesik.tabletopwarhammer.library.presentation.library_item

import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LibraryItemViewModel {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state

    fun onEvent(event: LibraryItemEvent) {
        when (event) {
            is LibraryItemEvent.LoadItem -> loadItem()
        }
    }

    private fun loadItem() {
        _state.update { it.copy(
            error = null,
        ) }
    }
}
