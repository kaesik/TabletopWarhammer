package com.kaesik.tabletopwarhammer.library.presentation.library_item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kaesik.tabletopwarhammer.library.data.library.LibraryEnum
import org.koin.androidx.compose.koinViewModel

@Composable
fun LibraryItemScreenRoot(
    viewModel: AndroidLibraryItemViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    itemId: String,
    fromTable: LibraryEnum
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true) {
        viewModel.onEvent(
            LibraryItemEvent.InitItem(
                itemId = itemId,
                fromTable = fromTable
            )
        )
    }
    LibraryItemScreen(
        state = state,
        onEvent = { event ->
            when (event) {
                is LibraryItemEvent.OnBackClick -> onBackClick()
                is LibraryItemEvent.OnFavoriteClick -> onFavoriteClick()
                else -> Unit
            }

            viewModel.onEvent(event)
        }
    )
}

@Composable
fun LibraryItemScreen(
    state: LibraryItemState,
    onEvent: (LibraryItemEvent) -> Unit
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text("LibraryItemScreen")
                Text(state.libraryItem?.name ?: "Loading...")
            }
        }
    }
}

@Preview
@Composable
fun LibraryItemScreenPreview() {
    LibraryItemScreen(
        state = LibraryItemState(),
        onEvent = {}
    )
}
