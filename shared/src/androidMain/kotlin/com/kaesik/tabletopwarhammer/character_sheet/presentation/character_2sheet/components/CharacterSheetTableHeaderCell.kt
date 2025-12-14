package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun RowScope.CharacterSheetTableHeaderCell(
    text: String,
    weight: Float = 1f,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier
            .fillMaxWidth()
            .weight(weight)
            .padding(horizontal = 8.dp),
        color = Brown1,
        textAlign = TextAlign.Center
    )
}
