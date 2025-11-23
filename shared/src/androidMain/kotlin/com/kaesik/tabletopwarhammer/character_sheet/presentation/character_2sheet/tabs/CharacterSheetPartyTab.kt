package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.presentation.components.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.components.SectionTitle

@Composable
fun CharacterSheetPartyTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (character.partyName.isNotBlank()) {
            item { SectionTitle("Party") }
            item { InfoText("Name", character.partyName) }
            item { InfoText("Short-Term Ambition", character.partyAmbitionShortTerm) }
            item { InfoText("Long-Term Ambition", character.partyAmbitionLongTerm) }
            character.partyMembers.forEachIndexed { index, member ->
                item { InfoText("Member ${index + 1}", member) }
            }
        } else {
            item { Text("No party", style = MaterialTheme.typography.bodyMedium) }
        }
    }
}
