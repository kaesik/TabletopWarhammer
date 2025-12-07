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
fun CharacterSheetAttributesTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { WarhammerSectionTitle("Attributes") }

        item {
            WarhammerInfoRow(
                label = "Weapon Skill",
                value = character.weaponSkill.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Ballistic Skill",
                value = character.ballisticSkill.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Strength",
                value = character.strength.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Toughness",
                value = character.toughness.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Initiative",
                value = character.initiative.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Agility",
                value = character.agility.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Dexterity",
                value = character.dexterity.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Intelligence",
                value = character.intelligence.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Willpower",
                value = character.willPower.joinToString("/")
            )
        }
        item {
            WarhammerInfoRow(
                label = "Fellowship",
                value = character.fellowship.joinToString("/")
            )
        }
    }
}
