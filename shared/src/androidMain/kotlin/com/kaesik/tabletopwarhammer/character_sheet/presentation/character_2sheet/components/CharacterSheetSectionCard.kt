package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun CharacterSheetSectionCard(
    modifier: Modifier = Modifier,
    header: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Brown1)
    ) {
        Column {
            if (header != null) {
                header()
                HorizontalDivider(thickness = 1.dp, color = Brown1)
            }
            content()
        }
    }
}
