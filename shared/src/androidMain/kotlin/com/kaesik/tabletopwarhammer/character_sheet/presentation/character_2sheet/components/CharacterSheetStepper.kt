package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun CharacterSheetStepper(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .background(Brown1)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "<",
            modifier = Modifier
                .clickable { onDecrement() }
                .padding(horizontal = 4.dp),
            color = Black1,
            textAlign = TextAlign.Center
        )
        Text(
            text = value.toString(),
            modifier = Modifier.padding(horizontal = 4.dp),
            color = Black1,
            textAlign = TextAlign.Center
        )
        Text(
            text = ">",
            modifier = Modifier
                .clickable { onIncrement() }
                .padding(horizontal = 4.dp),
            color = Black1,
            textAlign = TextAlign.Center
        )
    }
}
