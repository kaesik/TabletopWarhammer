package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerNumberField
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerSectionTitle
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerTextField
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

@Composable
fun CharacterSheetPointsTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    val currentXp = character.experience.getOrNull(0) ?: 0
    val spentXp = character.experience.getOrNull(1) ?: 0
    val totalXp = character.experience.getOrNull(2) ?: 0

    fun updateXp(index: Int, newValue: Int) {
        val list = character.experience.toMutableList()
        while (list.size < 3) list.add(0)
        list[index] = newValue
        onCharacterChange(character.copy(experience = list))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { WarhammerSectionTitle("Experience") }

        item {
            WarhammerNumberField(
                label = "Current XP",
                value = currentXp,
                onValueChange = { updateXp(0, it) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Spent XP",
                value = spentXp,
                onValueChange = { updateXp(1, it) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Total XP",
                value = totalXp,
                onValueChange = { updateXp(2, it) }
            )
        }

        item { WarhammerSectionTitle("Fate & Resilience") }

        item {
            WarhammerNumberField(
                label = "Fate",
                value = character.fate,
                onValueChange = { onCharacterChange(character.copy(fate = it)) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Fortune",
                value = character.fortune,
                onValueChange = { onCharacterChange(character.copy(fortune = it)) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Resilience",
                value = character.resilience,
                onValueChange = { onCharacterChange(character.copy(resilience = it)) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Resolve",
                value = character.resolve,
                onValueChange = { onCharacterChange(character.copy(resolve = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Motivation",
                value = character.motivation,
                onValueChange = { onCharacterChange(character.copy(motivation = it)) }
            )
        }
    }
}
