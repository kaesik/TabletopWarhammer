package com.kaesik.tabletopwarhammer.library.presentation.library_3item

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class AndroidLibraryItemViewModel(
    private val libraryClient: LibraryClient,
) : ViewModel() {

    private val viewModel by lazy {
        LibraryItemViewModel(
            libraryClient = libraryClient,
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryItemEvent) {
        viewModel.onEvent(event)
    }
}
