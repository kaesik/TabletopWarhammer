package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun CharacterSheetVerticalDivider(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Brown1)
    )
}
