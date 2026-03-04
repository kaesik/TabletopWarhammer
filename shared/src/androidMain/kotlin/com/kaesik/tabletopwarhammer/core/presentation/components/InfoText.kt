package com.kaesik.tabletopwarhammer.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Brown2

@Composable
fun InfoText(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = "$label:",
            style = typography.bodyMedium,
            color = Brown1,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = typography.bodyMedium,
            color = Brown2,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview
@Composable
fun InfoTextPreview() {
    InfoText(label = "Label", value = "Value")
}
