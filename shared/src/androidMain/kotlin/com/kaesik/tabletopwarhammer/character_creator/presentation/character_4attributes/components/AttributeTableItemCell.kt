package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun AttributeTableItemCell(text: String, isDragged: Boolean = false) {
    Box(
        modifier = Modifier
            .background(if (isDragged) MaterialTheme.colorScheme.primary.copy(alpha = 0.2f) else Color.Transparent)
            .padding(4.dp)
    ) {
        Text(text)
    }
}
