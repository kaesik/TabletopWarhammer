package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient

class AndroidLibraryViewModel(
    private val libraryClient: LibraryClient,
    private val fromTable: String
) : ViewModel() {

    private val viewModel by lazy {
        LibraryViewModel(
            libraryClient = libraryClient,
            fromTable = fromTable
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryEvent) {
        viewModel.onEvent(event)
    }
}
