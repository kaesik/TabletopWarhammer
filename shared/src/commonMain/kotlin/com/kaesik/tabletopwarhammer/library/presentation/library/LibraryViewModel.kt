package com.kaesik.tabletopwarhammer.library.presentation.library

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.domain.library.Library
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryViewModel(
    private val library: Library,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(LibraryState())
    val state = _state

    private var loadLibraryJob: Job? = null

    fun onEvent(event: LibraryEvent) {
        when (event) {
            is LibraryEvent.LoadLibrary -> loadLibrary(state.value, event.fromTable)
        }
    }

    private fun loadLibrary(
        state: LibraryState,
        fromTable: String,
    ) {
        if (state.isLoading) return

        println("LibraryViewModel.loadLibrary: $fromTable")

        loadLibraryJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            when (val result = library.loadLibrary(fromTable)) {
                is Resource.Success -> {
                    _state.update { it.copy(
                        result = result.data,
                        isLoading = false
                    ) }
                }
                is Resource.Error -> {
                    _state.update { it.copy(
                        error = (result.throwable as? LibraryException)?.error,
                        isLoading = false
                    ) }
                }
            }
        }
    }
}
