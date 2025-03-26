package com.kaesik.tabletopwarhammer.library.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

@Composable
fun LibraryList(
    libraryList: List<LibraryItem>,
    onLibraryItemClick: (LibraryItem) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = libraryList,
            key = { it.id }
        ) { libraryItem ->
            LibraryListItem(
                libraryItem = libraryItem,
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = {
                    onLibraryItemClick(libraryItem)
                },
            )
        }
    }
}

@Composable
@Preview
fun LibraryListPreview() {
    LibraryList(
        libraryList = listOf(
            AttributeItem(
                id = "1",
                name = "Attribute 1",
                description = "Attribute 1 description",
                shortName = "A1",
                page = 1
            ),
            AttributeItem(
                id = "2",
                name = "Attribute 2",
                description = "Attribute 2 description",
                shortName = "A2",
                page = 2
            ),
            AttributeItem(
                id = "3",
                name = "Attribute 3",
                description = "Attribute 3 description",
                shortName = "A3",
                page = 3
            ),
        ),
        onLibraryItemClick = {},
    )
}
