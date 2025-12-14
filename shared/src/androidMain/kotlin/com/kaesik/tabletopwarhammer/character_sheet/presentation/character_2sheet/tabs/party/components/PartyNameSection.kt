package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTextField
import com.kaesik.tabletopwarhammer.core.theme.Black1

@Composable
fun PartyNameSection(
    name: String,
    onNameChange: (String) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Party Name") }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black1)
                .padding(8.dp)
        ) {
            CharacterSheetTextField(
                value = name,
                onValueChange = onNameChange,
                singleLine = true
            )
        }
    }
}
