package com.kaesik.tabletopwarhammer.library.presentation.library_2list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryListViewModel(
    private val libraryClient: LibraryClient
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryListState())
    val state = _state.asStateFlow()

    private var loadLibraryListJob: Job? = null

    fun onEvent(event: LibraryListEvent) {
        when (event) {
            is LibraryListEvent.InitList -> {
                loadLibraryList(event.fromTable)
            }

            else -> Unit
        }
    }


    private fun loadLibraryList(fromTable: LibraryEnum) {
        loadLibraryListJob?.cancel()
        println("LibraryListViewModel:loadLibraryList fromTable: $fromTable")

        loadLibraryListJob = viewModelScope.launch {
            val libraryList = libraryClient.getLibraryList(
                fromTable = fromTable
            )
            _state.value = state.value.copy(
                libraryList = libraryList
            )
            println("LibraryListViewModel:loadLibraryList job: $libraryList")
        }
    }
}
