package com.kaesik.tabletopwarhammer.library.presentation.library_item

import androidx.lifecycle.ViewModel

class AndroidLibraryItemViewModel : ViewModel() {

    private val viewModel by lazy {
        LibraryItemViewModel()
    }

    val state = viewModel.state

    fun onEvent(event: LibraryItemEvent) {
        viewModel.onEvent(event)
    }
}
