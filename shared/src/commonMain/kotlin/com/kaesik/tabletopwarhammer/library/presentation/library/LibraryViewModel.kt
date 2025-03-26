package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel(
    private val libraryList: List<LibraryItem>
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private var loadLibraryJob: Job? = null

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnLibrarySelect -> {

            }
        }
    }
}
