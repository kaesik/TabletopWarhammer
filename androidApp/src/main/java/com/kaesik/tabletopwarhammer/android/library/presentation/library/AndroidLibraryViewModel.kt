package com.kaesik.tabletopwarhammer.android.library.presentation.library

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryViewModel

class AndroidLibraryViewModel(
    private val libraryList: List<LibraryItem>
) : ViewModel() {

    private val viewModel by lazy {
        LibraryViewModel(
            libraryList = libraryList
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryEvent) {
        viewModel.onEvent(event)
    }
}
