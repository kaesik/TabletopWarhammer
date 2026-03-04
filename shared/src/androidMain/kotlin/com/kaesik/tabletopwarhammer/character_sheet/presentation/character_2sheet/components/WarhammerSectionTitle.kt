package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WarhammerSectionTitle(text: String) {
    Text(
        text = text,
        style = typography.titleLarge,
        modifier = Modifier.padding(vertical = 4.dp)
    )
}
