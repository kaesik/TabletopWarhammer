package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private var loadLibraryJob: Job? = null

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.OnLibrarySelect -> {
                loadLibrary()
            }
        }
    }

    private fun loadLibrary() {
        println("loadLibrary")
    }
}
