package com.kaesik.tabletopwarhammer.character_creator.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DiceThrow(
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .height(100.dp)
            .padding(8.dp),
        shape = androidx.compose.material3.MaterialTheme.shapes.large,
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Throw dice",
            )
            IconButton (
                onClick = onClick,
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Throw dice"
                )
            }
        }
    }

}

@Composable
@Preview
fun DiceThrowPreview() {
    DiceThrow(
        onClick = {}
    )
}
