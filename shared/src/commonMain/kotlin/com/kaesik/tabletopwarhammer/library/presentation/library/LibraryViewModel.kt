package com.kaesik.tabletopwarhammer.library.presentation.library

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val libraryClient: LibraryClient,
    private val fromTable: String
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryState())
    val state = _state.asStateFlow()

    private var loadLibraryJob: Job? = null

    fun onEvent(event: LibraryEvent) {
        when (event) {
            else -> {}
        }
    }
}
