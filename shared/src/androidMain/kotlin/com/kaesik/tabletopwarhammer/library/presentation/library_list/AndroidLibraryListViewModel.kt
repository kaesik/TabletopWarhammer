package com.kaesik.tabletopwarhammer.library.presentation.library_list

import androidx.lifecycle.ViewModel

class AndroidLibraryListViewModel(
    private val id: String
) : ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel(
            id = id
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }
}
