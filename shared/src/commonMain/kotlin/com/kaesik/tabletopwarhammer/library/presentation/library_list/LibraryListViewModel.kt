package com.kaesik.tabletopwarhammer.library.presentation.library_list

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.domain.library.Library
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryListViewModel(
    private val library: Library,
    private val id: String,
    coroutineScope: CoroutineScope?
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(LibraryListState())
    val state = _state.asStateFlow()

    private var loadLibraryListJob: Job? = null

    fun onEvent(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.LoadLibrary -> {

            }
        }
    }


//    private fun loadLibrary(
//        state: LibraryListState,
//        fromTable: String,
//    ) {
//        if (state.isLoading) return
//
//        println("LibraryViewModel.loadLibrary: $fromTable")
//
//        loadLibraryJob = viewModelScope.launch {
//            _state.update { it.copy(isLoading = true) }
//
//            when (val result = library.loadLibrary(fromTable)) {
//                is Resource.Success -> {
//                    _state.update { it.copy(
//                        result = result.data,
//                        isLoading = false
//                    ) }
//                }
//                is Resource.Error -> {
//                    _state.update { it.copy(
//                        error = (result.throwable as? LibraryException)?.error,
//                        isLoading = false
//                    ) }
//                }
//            }
//        }
//    }
}
