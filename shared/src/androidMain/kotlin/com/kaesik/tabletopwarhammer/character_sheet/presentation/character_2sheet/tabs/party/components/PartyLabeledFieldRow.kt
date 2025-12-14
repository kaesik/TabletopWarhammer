package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTextField
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun PartyLabeledFieldRow(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            color = Brown1
        )
        Box(
            modifier = Modifier.weight(3f)
        ) {
            CharacterSheetTextField(
                label = null,
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine
            )
        }
    }
}
