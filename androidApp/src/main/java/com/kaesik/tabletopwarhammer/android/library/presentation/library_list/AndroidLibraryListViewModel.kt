package com.kaesik.tabletopwarhammer.android.library.presentation.library_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.library.domain.library.Library
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

@HiltViewModel
class AndroidLibraryListViewModel @Inject constructor(
    private val library: Library,
    private val id: String,
    private val libraryList: List<LibraryItem>,
): ViewModel() {

    private val viewModel by lazy {
        LibraryListViewModel(
            library = library,
            id = id,
            libraryList = libraryList,
            coroutineScope = viewModelScope
        )
    }

    val state = viewModel.state

    fun onEvent(event: LibraryListEvent) {
        viewModel.onEvent(event)
    }

    fun setLibraryList(items: List<LibraryItem>) {
        viewModel.setLibraryList(items)
    }
}
