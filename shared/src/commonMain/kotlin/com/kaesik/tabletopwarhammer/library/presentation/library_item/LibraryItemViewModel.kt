package com.kaesik.tabletopwarhammer.library.presentation.library_item

import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryException
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LibraryItemViewModel {
    private val _state = MutableStateFlow(LibraryItemState())
    val state = _state.asStateFlow()

    fun onEvent(event: LibraryItemEvent) {
        when (event) {
            is LibraryItemEvent.LoadItem -> {

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
