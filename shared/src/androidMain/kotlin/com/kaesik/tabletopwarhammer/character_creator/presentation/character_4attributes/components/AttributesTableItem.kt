package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AttributesTableItem(
    attributeName: String,
    diceThrow: String,
    baseValue: String,
    totalValue: String,
    isDragged: Boolean = false,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(
                if (isDragged) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                else Color.Transparent
            ),
        shadowElevation = if (isDragged) 4.dp else 0.dp
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AttributeTableItemCell(attributeName, modifier = Modifier.weight(1f))
            AttributeTableItemCell(baseValue, modifier = Modifier.weight(0.5f))
            AttributeTableItemCell(totalValue, modifier = Modifier.weight(0.5f))
        }
    }
}

@Composable
fun AttributesTableItemHeader() {
    Surface(shadowElevation = 4.dp) {
        Row(modifier = Modifier.padding(4.dp).fillMaxWidth()) {
            AttributeTableItemCell("Attribute")
            AttributeTableItemCell("Base Value")
            AttributeTableItemCell("Total Value")
            AttributeTableItemCell("Dice Throw")
        }
    }
}
