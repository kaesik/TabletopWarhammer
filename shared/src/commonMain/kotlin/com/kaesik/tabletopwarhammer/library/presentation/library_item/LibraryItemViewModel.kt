package com.kaesik.tabletopwarhammer.library.presentation.library_item

import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LibraryItemViewModel {
    private val _state = MutableStateFlow(LibraryItemState())
    val state = _state.asStateFlow()

    private var loadLibraryItemJob: Job? = null

    fun onEvent(event: LibraryItemEvent) {
        when (event) {
            is LibraryItemEvent.OnFavoriteClick -> {

            }

            is LibraryItemEvent.OnBackClick -> {

            }
        }
    }


//    private fun loadItem() {
//        val currentState = _state.value
//        if (currentState.isLoading) return
//
//        loadLibraryListJob = viewModelScope.launch {
//            _state.update { it.copy(isLoading = true) }
//
//            when (val result = library.loadLibraryList(id, currentState.list)) {
//                is Resource.Success -> {
//                    _state.update {
//                        it.copy(
//                            result = result.data,
//                            isLoading = false
//                        )
//                    }
//                }
//                is Resource.Error -> {
//                    _state.update {
//                        it.copy(
//                            error = (result.throwable as? LibraryException)?.error,
//                            isLoading = false
//                        )
//                    }
//                }
//            }
//        }
//    }
}
