package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class LibraryListViewModel(

) {
    private val _state = MutableStateFlow(LibraryListState())
    val state = _state

    fun onEvent(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.LoadItem -> loadItem()
        }
    }

    private fun loadItem() {
        _state.update { it.copy(
            error = null,
        ) }
    }
}
