package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.components.CharacterCreatorButton
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem

@Composable
fun AllocationCard(
    attributes: List<AttributeItem>,
    values: List<Int>,
    pointsLeft: Int,
    onPlus: (Int) -> Unit,
    onMinus: (Int) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Allocate 100 points", style = MaterialTheme.typography.titleMedium)
            Text("Points left: $pointsLeft")

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                itemsIndexed(attributes) { index, attr ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(attr.name)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CharacterCreatorButton(
                                text = "−",
                                onClick = { onMinus(index) },
                                enabled = (values.getOrNull(index) ?: 0) > 0
                            )
                            Spacer(Modifier.width(8.dp))
                            Text(values.getOrNull(index)?.toString() ?: "0")
                            Spacer(Modifier.width(8.dp))
                            CharacterCreatorButton(
                                text = "+",
                                onClick = { onPlus(index) },
                                enabled = pointsLeft > 0
                            )
                        }
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CharacterCreatorButton(text = "Cancel", onClick = onCancel)
                CharacterCreatorButton(
                    text = "Confirm",
                    onClick = onConfirm,
                    enabled = pointsLeft == 0
                )
            }
        }
    }
}
