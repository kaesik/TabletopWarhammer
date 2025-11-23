package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue
import com.kaesik.tabletopwarhammer.core.presentation.components.InfoText
import com.kaesik.tabletopwarhammer.core.presentation.components.SectionTitle

@Composable
fun CharacterSheetSkillsTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Basic Skills") }
        character.basicSkills
            .groupBy { it[0] }
            .forEach { (name, entries) ->
                val bonus = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
                val base = getAttributeValue(character, attr)
                item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
            }

        item { SectionTitle("Advanced Skills") }
        character.advancedSkills
            .groupBy { it[0] }
            .forEach { (name, entries) ->
                val bonus = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
                val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
                val base = getAttributeValue(character, attr)
                item { InfoText("$name ($attr)", "$bonus / $base / ${bonus + base}") }
            }
    }
}
