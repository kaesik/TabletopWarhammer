package com.kaesik.tabletopwarhammer.android.library.presentation.library_list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListViewModel

class AndroidLibraryListViewModel(
    private val libraryItem: LibraryItem
) : ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel(
            libraryItem = libraryItem
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }
}
