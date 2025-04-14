package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TrappingTableItem(
    trapping: String,
) {
    Surface(
        modifier = Modifier,
        shadowElevation = 4.dp,
    ) {
        Row {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.ShoppingCart,
                    contentDescription = null,
                )
            }
            Box(
                modifier = Modifier.weight(3f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = trapping,
                )
            }
        }
    }
}

@Composable
@Preview
fun TrappingTableItemPreview() {
    TrappingTableItem(
        trapping = "Trapping Name",
    )
}
