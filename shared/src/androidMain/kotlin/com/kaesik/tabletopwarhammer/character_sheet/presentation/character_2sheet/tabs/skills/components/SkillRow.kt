package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetStepperDialog
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Light1

@Composable
fun SkillRow(row: SkillRowState) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.name,
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Light1
        )

        CharacterSheetVerticalDivider()

        Text(
            text = "${row.characteristicName} ${row.characteristicValue}",
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        CharacterSheetVerticalDivider()

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Brown1)
                .clickable { showDialog.value = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = row.advances.toString(),
                color = Black1,
                textAlign = TextAlign.Center
            )
        }

        CharacterSheetVerticalDivider()

        Text(
            text = row.skill.toString(),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Light1,
            textAlign = TextAlign.Center
        )
    }

    if (showDialog.value) {
        CharacterSheetStepperDialog(
            title = row.name,
            initialValue = row.advances,
            onDismiss = { showDialog.value = false },
            onConfirm = { newAdv ->
                showDialog.value = false
                row.onAdvancesChange(newAdv)
            }
        )
    }
}
