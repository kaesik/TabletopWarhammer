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
fun CharacterSheetPointsTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Experience") }
        item { InfoText("Current / Spent / Total", character.experience.joinToString(" / ")) }

        item { SectionTitle("Fate & Resilience") }
        item { InfoText("Fate", character.fate.toString()) }
        item { InfoText("Fortune", character.fortune.toString()) }
        item { InfoText("Resilience", character.resilience.toString()) }
        item { InfoText("Resolve", character.resolve.toString()) }
        item { InfoText("Motivation", character.motivation) }
    }
}
