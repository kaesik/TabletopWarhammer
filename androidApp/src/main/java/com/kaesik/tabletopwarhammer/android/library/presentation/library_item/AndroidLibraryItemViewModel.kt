package com.kaesik.tabletopwarhammer.android.library.presentation.library_item

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidLibraryItemViewModel @Inject constructor(

): ViewModel() {

    private val viewModel by lazy {
        LibraryItemViewModel()
    }

    val state = viewModel.state

    fun onEvent(event: LibraryItemEvent) {
        viewModel.onEvent(event)
    }
}
