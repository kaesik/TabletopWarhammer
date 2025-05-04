package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailInputRandom(
    label: String,
    value: String,
    infoText: String = "",
    onValueChange: (String) -> Unit,
    onRandomClick: () -> Unit,
) {
    var showInfo by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = label, style = MaterialTheme.typography.labelLarge)
            Spacer(Modifier.width(4.dp))
            IconButton(onClick = { showInfo = !showInfo }) {
                Icon(Icons.Default.Info, contentDescription = "Info")
            }
        }
        if (showInfo) {
            Text(
                text = infoText,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                maxLines = 1,
                singleLine = true,
            )
            Spacer(Modifier.width(8.dp))
            IconButton(onClick = onRandomClick) {
                Icon(Icons.Default.Refresh, contentDescription = "Randomize")
            }
        }
    }
}

@Composable
@Preview
fun PreviewDetailInputRandom() {
    var name by remember { mutableStateOf("") }

    DetailInputRandom(
        label = "Imię",
        value = name,
        onValueChange = { name = it },
        onRandomClick = { name = listOf("Borin", "Gisella", "Thrain").random() },
        infoText = "TODO()"
    )
}
