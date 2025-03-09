package com.kaesik.tabletopwarhammer.android.library.presentation.library_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListState

@Composable
fun LibraryListScreen(
    state: LibraryListState,
    onEvent: (LibraryListEvent) -> Unit,
    libraryList: List<LibraryItem>
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Text("Item List")
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
        libraryList = emptyList()
    )
}
