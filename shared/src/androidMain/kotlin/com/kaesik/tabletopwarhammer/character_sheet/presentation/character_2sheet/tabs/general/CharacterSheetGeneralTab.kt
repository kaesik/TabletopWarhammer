package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general.components.GeneralAmbitionsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general.components.GeneralInfoSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetGeneralTab(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            GeneralInfoSection(
                character = character,
                onEvent = onEvent
            )
        }

        item {
            GeneralAmbitionsSection(
                character = character,
                onEvent = onEvent
            )
        }
    }
}

@Preview
@Composable
fun CharacterSheetGeneralTabPreview() {
    CharacterSheetGeneralTab(
        character = CharacterItem.default(),
        onEvent = {}
    )
}
