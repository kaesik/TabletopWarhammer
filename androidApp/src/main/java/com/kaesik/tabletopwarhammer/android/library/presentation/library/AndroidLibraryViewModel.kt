package com.kaesik.tabletopwarhammer.android.library.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.library.domain.library.Library
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidLibraryViewModel @Inject constructor(
    private val library: Library
): ViewModel()  {

    private val viewModel by lazy {
    LibraryViewModel(
        library = library,
        coroutineScope = viewModelScope
    )
}

    val state = viewModel.state

    fun onEvent(event: LibraryEvent) {
        viewModel.onEvent(event)
    }
}
