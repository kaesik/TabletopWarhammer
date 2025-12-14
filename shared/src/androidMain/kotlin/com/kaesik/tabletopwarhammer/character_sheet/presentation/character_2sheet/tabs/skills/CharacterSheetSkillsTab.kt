package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.components.SkillsSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetSkillsTab(
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
            SkillsSection(
                title = "Basic Skills",
                entries = character.basicSkills,
                isBasic = true,
                character = character,
                onEvent = onEvent
            )
        }

        item {
            SkillsSection(
                title = "Advanced Skills",
                entries = character.advancedSkills,
                isBasic = false,
                character = character,
                onEvent = onEvent
            )
        }
    }
}

@Preview
@Composable
private fun CharacterSheetSkillsTabPreview() {
    val character = CharacterItem.default()

    CharacterSheetSkillsTab(
        character = character,
        onEvent = {}
    )
}
