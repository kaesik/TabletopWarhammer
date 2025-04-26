package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
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
fun PointPicker(
    name: String,
    value: Int,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    canIncrease: Boolean,
    canDecrease: Boolean,
) {
    ElevatedCard(
        modifier = Modifier
            .width(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = name)

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(
                    onClick = { onDecrease() },
                    enabled = canDecrease // <- blokada zmniejszania
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowLeft,
                        contentDescription = "Decrease $name"
                    )
                }

                Text(text = value.toString())

                IconButton(
                    onClick = { onIncrease() },
                    enabled = canIncrease // <- blokada zwiększania
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "Increase $name"
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun PointPickerPreview() {
    PointPicker(
        name = "Test",
        value = 10,
        onIncrease = {},
        onDecrease = {},
        canIncrease = true,
        canDecrease = false,
    )
}
