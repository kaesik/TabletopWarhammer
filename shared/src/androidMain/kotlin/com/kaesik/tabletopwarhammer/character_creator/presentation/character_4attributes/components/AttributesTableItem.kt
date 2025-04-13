package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun AttributesTableItem(
    attribute: String,
    diceThrow: String,
    baseAttributeValue: String,
    totalAttributeValue: String,
) {
    Surface(
        modifier = Modifier,
        shadowElevation = 4.dp,
    ) {
        Row {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = attribute,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = diceThrow,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = baseAttributeValue,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = totalAttributeValue,
                )
            }
        }
    }
}

@Composable
@Preview
fun AttributesTableItemPreview() {
    AttributesTableItem(
        attribute = "Strength",
        diceThrow = "12",
        baseAttributeValue = "20",
        totalAttributeValue = "12",
    )
}
