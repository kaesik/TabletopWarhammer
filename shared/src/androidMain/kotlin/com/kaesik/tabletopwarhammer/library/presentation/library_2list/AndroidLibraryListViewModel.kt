package com.kaesik.tabletopwarhammer.library.presentation.library_2list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class AndroidLibraryListViewModel(
    private val libraryClient: LibraryClient
) : ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel(
            libraryClient = libraryClient
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }
}
