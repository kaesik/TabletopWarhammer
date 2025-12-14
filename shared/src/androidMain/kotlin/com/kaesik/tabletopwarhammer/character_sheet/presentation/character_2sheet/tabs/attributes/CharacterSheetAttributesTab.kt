package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components.AttributesCharacteristicsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components.AttributesWoundsSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetAttributesTab(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            AttributesWoundsSection(
                character = character,
                onEvent = onEvent
            )
        }

        item {
            AttributesCharacteristicsSection(
                character = character,
                onEvent = onEvent
            )
        }
    }
}

@Preview
@Composable
private fun CharacterSheetAttributesTabPreview() {
    CharacterSheetAttributesTab(
        character = CharacterItem.default(),
        onEvent = {}
    )
}
