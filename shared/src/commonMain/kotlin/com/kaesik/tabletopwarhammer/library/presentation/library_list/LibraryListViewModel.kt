package com.kaesik.tabletopwarhammer.library.presentation.library_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryListViewModel(
    private val libraryClient: LibraryClient,
    private val id: String
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryListState())
    val state = _state.asStateFlow()

    private var loadLibraryListJob: Job? = null

    fun onEvent(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.InitList -> {
                loadLibraryList(event.fromTable)
            }
            is LibraryListEvent.OnLibraryItemSelect -> {
                loadLibraryItem(itemId = id)
            }
        }
    }


    private fun loadLibraryList(fromTable: LibraryEnum) {
        loadLibraryListJob?.cancel()
        println("LibraryListViewModel:loadLibraryList fromTable: $fromTable")

        loadLibraryListJob = viewModelScope.launch {
            val job = libraryClient.getLibraryList(fromTable = fromTable)
            _state.value = state.value.copy(
                libraryList = job
            )
            println("LibraryListViewModel:loadLibraryList job: $job")
        }
    }

    private fun loadLibraryItem(itemId: String) {
        println("loadLibraryItem: $itemId")
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
//                    _state.update {
//                        it.copy(
//                            result = result.data,
//                            isLoading = false
//                        )
//                    }
//                }
//
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
