package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
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
import com.kaesik.tabletopwarhammer.core.theme.Red

@Composable
fun AttributeRow(
    row: AttributeRowState,
    upgradable: Boolean = true
) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = row.shortLabel,
                color = Light1,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = row.fullLabel,
                tint = Red,
                modifier = Modifier.size(16.dp)
            )
        }

        CharacterSheetVerticalDivider()

        Text(
            text = row.base.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )

        CharacterSheetVerticalDivider()

        IconButton(
            onClick = { if (upgradable) showDialog.value = true },
            colors = iconButtonColors(
                containerColor = if (upgradable) Brown1 else Black1
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Text(
                text = row.advances.toString(),
                color = if (upgradable) Black1 else Brown1
            )
        }

        CharacterSheetVerticalDivider()

        Text(
            text = row.current.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Light1,
            textAlign = TextAlign.Center
        )
    }

    if (showDialog.value) {
        CharacterSheetStepperDialog(
            title = row.fullLabel,
            initialValue = row.advances,
            onDismiss = { showDialog.value = false },
            onConfirm = { newAdv ->
                showDialog.value = false
                row.onAdvancesChange(newAdv)
            }
        )
    }
}
