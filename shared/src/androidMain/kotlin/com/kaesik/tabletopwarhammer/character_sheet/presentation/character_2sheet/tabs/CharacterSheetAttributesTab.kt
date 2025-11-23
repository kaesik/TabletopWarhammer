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
fun CharacterSheetAttributesTab(character: CharacterItem) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { SectionTitle("Attributes") }
        item { InfoText("Weapon Skill", character.weaponSkill.joinToString("/")) }
        item { InfoText("Ballistic Skill", character.ballisticSkill.joinToString("/")) }
        item { InfoText("Strength", character.strength.joinToString("/")) }
        item { InfoText("Toughness", character.toughness.joinToString("/")) }
        item { InfoText("Initiative", character.initiative.joinToString("/")) }
        item { InfoText("Agility", character.agility.joinToString("/")) }
        item { InfoText("Dexterity", character.dexterity.joinToString("/")) }
        item { InfoText("Intelligence", character.intelligence.joinToString("/")) }
        item { InfoText("Willpower", character.willPower.joinToString("/")) }
        item { InfoText("Fellowship", character.fellowship.joinToString("/")) }
    }
}
