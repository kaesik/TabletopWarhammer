package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.lifecycle.ViewModel

class AndroidLibraryViewModel(
) : ViewModel() {

    private val viewModel by lazy {
        LibraryViewModel(
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryEvent) {
        viewModel.onEvent(event)
    }
}
