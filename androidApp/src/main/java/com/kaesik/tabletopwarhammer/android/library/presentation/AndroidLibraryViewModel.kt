package com.kaesik.tabletopwarhammer.android.library.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.presentation.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.LibraryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidLibraryViewModel @Inject constructor(

): ViewModel()  {

    private val viewModel by lazy {
    LibraryViewModel(
    )
}

    val state = viewModel.state

    fun onEvent(event: LibraryEvent) {
        viewModel.onEvent(event)
    }
}
