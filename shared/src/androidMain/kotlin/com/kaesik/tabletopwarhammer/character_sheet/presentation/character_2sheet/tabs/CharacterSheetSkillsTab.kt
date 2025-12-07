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
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue

@Composable
fun CharacterSheetSkillsTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { WarhammerSectionTitle("Basic Skills") }
        character.basicSkills
            .groupBy { it[0] }
            .forEach { (name, entries) ->
                val bonus = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
                val base = getAttributeValue(character, attr)
                item {
                    WarhammerInfoRow(
                        label = "$name ($attr)",
                        value = "$bonus / $base / ${bonus + base}"
                    )
                }
            }

        item { WarhammerSectionTitle("Advanced Skills") }
        character.advancedSkills
            .groupBy { it[0] }
            .forEach { (name, entries) ->
                val bonus = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
                val base = getAttributeValue(character, attr)
                item {
                    WarhammerInfoRow(
                        label = "$name ($attr)",
                        value = "$bonus / $base / ${bonus + base}"
                    )
                }
            }
    }
}
