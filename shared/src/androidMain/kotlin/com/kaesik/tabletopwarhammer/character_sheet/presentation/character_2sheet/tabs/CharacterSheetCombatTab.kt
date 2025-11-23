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
fun CharacterSheetCombatTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Movement") }
        item { InfoText("Base", character.movement.toString()) }
        item { InfoText("Walk", character.walk.toString()) }
        item { InfoText("Run", character.run.toString()) }
    }
}
