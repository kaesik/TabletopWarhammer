package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.presentation.components.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.components.SectionTitle

@Composable
fun CharacterSheetInventoryTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (character.trappings.isNotEmpty()) {
            item { SectionTitle("Trappings") }
            character.trappings.forEach { trap ->
                item { InfoText("-", trap) }
            }
        }

        item { SectionTitle("Wealth") }
        item { InfoText("Brass", character.wealth.getOrNull(0)?.toString() ?: "0") }
        item { InfoText("Silver", character.wealth.getOrNull(1)?.toString() ?: "0") }
        item { InfoText("Gold", character.wealth.getOrNull(2)?.toString() ?: "0") }
    }
}
