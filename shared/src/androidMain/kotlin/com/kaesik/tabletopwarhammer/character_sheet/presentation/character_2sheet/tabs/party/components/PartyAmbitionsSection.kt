package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun PartyAmbitionsSection(
    shortTerm: String,
    longTerm: String,
    onShortTermChange: (String) -> Unit,
    onLongTermChange: (String) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Party Ambitions") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black1)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterSheetTextField(
                label = "Short Term Ambition",
                value = shortTerm,
                onValueChange = onShortTermChange
            )
            CharacterSheetTextField(
                label = "Long Term Ambition",
                value = longTerm,
                onValueChange = onLongTermChange
            )
        }
    }
}
