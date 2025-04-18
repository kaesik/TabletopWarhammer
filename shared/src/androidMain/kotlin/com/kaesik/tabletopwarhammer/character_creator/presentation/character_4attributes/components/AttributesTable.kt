package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem

@Composable
fun AttributesTable(
    attributes: List<AttributeItem>,
    diceThrow: List<String>?,
    baseAttributeValue: List<String>?,
    totalAttributeValue: List<String>?,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            AttributesTableItem(
                attribute = "Attribute",
                diceThrow = "Dice Throw",
                baseAttributeValue = "Base Value",
                totalAttributeValue = "Total Value",
            )
        }
        items(attributes.size) { index ->
            AttributesTableItem(
                attribute = attributes[index].name,
                diceThrow = diceThrow?.get(index) ?: "0",
                baseAttributeValue = baseAttributeValue?.get(index) ?: "0",
                totalAttributeValue = totalAttributeValue?.get(index) ?: "0",
            )
        }
    }
}

@Composable
@Preview
fun AttributesTablePreview() {
    val diceThrow = listOf("1", "2", "3", "4")
    val baseAttributeValue = listOf("10", "20", "30", "40")
    val totalAttributeValue = listOf("11", "22", "33", "44")

    AttributesTable(
        attributes = listOf(),
        diceThrow = diceThrow,
        baseAttributeValue = baseAttributeValue,
        totalAttributeValue = totalAttributeValue
    )
}
