package com.kaesik.tabletopwarhammer.android.library.presentation.library_item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemState

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
                Text("Item")
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
