package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents.components.TalentsSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

@Composable
fun CharacterSheetTalentsTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            TalentsSection(
                character = character,
                onCharacterChange = onCharacterChange
            )
        }
    }
}

@Preview
@Composable
private fun CharacterSheetTalentsTabPreview() {
    val character = CharacterItem.default().copy(
        talents = listOf(
            listOf("Brave"),
            listOf("Strong"),
            listOf("Brave"),
            listOf("Agile"),
        )
    )
    CharacterSheetTalentsTab(
        character = character,
        onCharacterChange = {}
    )
}
