package com.kaesik.tabletopwarhammer.android.library.presentation.library_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListState
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryListScreenRoot(
    viewModel: AndroidLibraryListViewModel = koinViewModel(),
    onLibraryItemSelect: (LibraryItem) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LibraryListScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryListEvent.OnLibraryItemSelect -> onLibraryItemSelect(event.libraryItem)
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
            item {
                Text("LibraryListScreen")
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
