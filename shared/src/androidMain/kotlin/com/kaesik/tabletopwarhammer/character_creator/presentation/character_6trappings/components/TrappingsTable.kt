package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem

@Composable
fun TrappingsTable(
    trappings: List<ItemItem>,
) {
    LazyColumn(
        modifier = Modifier
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(trappings.size) {
            TrappingTableItem(
                trapping = trappings[it],
            )
        }
    }
}

@Composable
@Preview
fun TrappingsTablePreview() {
    TrappingsTable(
        trappings = listOf(),
    )
}
