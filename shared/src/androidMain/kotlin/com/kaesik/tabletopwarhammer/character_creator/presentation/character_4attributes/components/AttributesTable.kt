package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DragHandle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyGridState

@Composable
fun AttributesTable(
    attributes: List<AttributeItem>,
    diceThrows: List<String>,
    baseAttributeValues: List<String>,
    totalAttributeValues: List<String>,
    onOrderChange: (List<String>) -> Unit,
    isReordering: Boolean,
    modifier: Modifier = Modifier
) {
    Column {
        AttributesTableItemHeader()
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(attributes) { index, attr ->
                    AttributesTableItem(
                        attributeName = attr.name,
                        diceThrow = diceThrows.getOrElse(index) { "" },
                        baseValue = baseAttributeValues.getOrElse(index) { "0" },
                        totalValue = totalAttributeValues.getOrElse(index) { "0" },
                        isDragged = isReordering
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))

            val gridState = rememberLazyGridState()
            val reorderableState = rememberReorderableLazyGridState(gridState) { from, to ->
                val newOrder = diceThrows.toMutableList().apply {
                    add(to.index, removeAt(from.index))
                }
                onOrderChange(newOrder)
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                state = gridState,
                modifier = Modifier
                    .weight(0.5f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(
                    diceThrows,
                    key = { index, value -> "$value-$index" }
                ) { index, diceThrow ->
                    ReorderableItem(reorderableState, key = "$diceThrow-$index") { isDragging ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isDragging) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                    else MaterialTheme.colorScheme.surface
                                )
                                .padding(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isReordering) {
                                    Text(
                                        text = diceThrow,
                                        modifier = Modifier.draggableHandle()
                                    )
                                } else {
                                    Text(text = diceThrow)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
