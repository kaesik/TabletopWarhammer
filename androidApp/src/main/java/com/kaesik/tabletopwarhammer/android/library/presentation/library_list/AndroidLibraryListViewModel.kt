package com.kaesik.tabletopwarhammer.android.library.presentation.library_list

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidLibraryListViewModel @Inject constructor(

): ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel()
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }
}
