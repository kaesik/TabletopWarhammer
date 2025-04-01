package com.kaesik.tabletopwarhammer.library.presentation.library_list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class AndroidLibraryListViewModel(
    private val libraryClient: LibraryClient,
    private val id: String,
    private val fromTable: String
) : ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel(
            libraryClient = libraryClient,
            id = id
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }
}
