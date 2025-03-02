package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LibraryListViewModel {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state

    fun onEvent(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.LoadItems -> loadItems()
        }
    }

    private fun loadItems() {
        _state.update { it.copy(
            error = null,
        ) }
    }
}
