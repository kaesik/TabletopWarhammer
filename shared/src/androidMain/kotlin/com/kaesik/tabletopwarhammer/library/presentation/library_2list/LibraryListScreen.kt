package com.kaesik.tabletopwarhammer.library.presentation.library_2list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.library.presentation.components.LibraryListItem
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryListScreenRoot(
    viewModel: AndroidLibraryListViewModel = koinViewModel(),
    onLibraryItemSelect: (String) -> Unit,
    fromTable: LibraryEnum
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(LibraryListEvent.InitList(fromTable))
    }
    LibraryListScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryListEvent.OnLibraryItemSelect -> onLibraryItemSelect(event.id)
                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun LibraryListScreen(
    state: LibraryListState,
    onEvent: (LibraryListEvent) -> Unit,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(state.libraryList) {
                LibraryListItem(
                    it,
                    { onEvent(LibraryListEvent.OnLibraryItemSelect(it.id)) },
                )
            }
        }
    }
}

@Preview
@Composable
fun LibraryListScreenPreview() {
    LibraryListScreen(
        state = LibraryListState(),
        onEvent = {},
    )
}
