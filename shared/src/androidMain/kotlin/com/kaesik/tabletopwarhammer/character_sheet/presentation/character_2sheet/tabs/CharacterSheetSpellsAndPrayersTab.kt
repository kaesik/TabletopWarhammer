package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerInfoRow
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerSectionTitle
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

@Composable
fun CharacterSheetSpellsAndPrayersTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (character.spells.isNotEmpty()) {
            item { WarhammerSectionTitle("Spells") }
            character.spells.forEach { spell ->
                val name = spell.getOrNull(0) ?: "?"
                val range = spell.getOrNull(2) ?: ""
                val effect = spell.getOrNull(4) ?: ""
                item {
                    WarhammerInfoRow(
                        label = name,
                        value = "Range: $range | Effect: $effect"
                    )
                }
            }
        }
        if (character.prayers.isNotEmpty()) {
            item { WarhammerSectionTitle("Prayers") }
            character.prayers.forEach { prayer ->
                val name = prayer.getOrNull(0) ?: "?"
                val range = prayer.getOrNull(2) ?: ""
                val effect = prayer.getOrNull(4) ?: ""
                item {
                    WarhammerInfoRow(
                        label = name,
                        value = "Range: $range | Effect: $effect"
                    )
                }
            }
        }
    }
}
