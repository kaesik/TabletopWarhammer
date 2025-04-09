package com.kaesik.tabletopwarhammer.library.presentation.library_item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LibraryItemViewModel(
    private val libraryClient: LibraryClient,
) : ViewModel() {
    private val _state = MutableStateFlow(LibraryItemState())
    val state = _state.asStateFlow()

    private var loadLibraryItemJob: Job? = null

    fun onEvent(event: LibraryItemEvent) {
        when (event) {
            is LibraryItemEvent.InitItem -> {
                loadLibraryItem(
                    event.itemId,
                    event.fromTable
                )
            }

            is LibraryItemEvent.OnFavoriteClick -> {

            }

            is LibraryItemEvent.OnBackClick -> {

            }
        }
    }

    private fun loadLibraryItem(itemId: String, fromTable: LibraryEnum) {
        loadLibraryItemJob?.cancel()
        println("LibraryItemViewModel:loadLibraryItem itemId: $itemId")

        loadLibraryItemJob = viewModelScope.launch {
            val libraryItem = libraryClient.getLibraryItem(
                itemId = itemId,
                fromTable = fromTable
            )
            _state.value = state.value.copy(
                libraryItem = libraryItem
            )
            println("LibraryItemViewModel:loadLibraryItem item: $libraryItem")
        }
    }
}
