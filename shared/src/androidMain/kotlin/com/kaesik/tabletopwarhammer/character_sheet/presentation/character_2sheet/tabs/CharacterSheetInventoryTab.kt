package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerInfoRow
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerNumberField
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerSectionTitle
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

@Composable
fun CharacterSheetInventoryTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    val brass = character.wealth.getOrNull(0) ?: 0
    val silver = character.wealth.getOrNull(1) ?: 0
    val gold = character.wealth.getOrNull(2) ?: 0

    fun updateWealth(index: Int, newValue: Int) {
        val list = character.wealth.toMutableList()
        while (list.size < 3) list.add(0)
        list[index] = newValue
        onCharacterChange(character.copy(wealth = list))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (character.trappings.isNotEmpty()) {
            item { WarhammerSectionTitle("Trappings") }
            character.trappings.forEach { trap ->
                item { WarhammerInfoRow("-", trap) }
            }
        }

        item { WarhammerSectionTitle("Wealth") }

        item {
            WarhammerNumberField(
                label = "Brass",
                value = brass,
                onValueChange = { updateWealth(0, it) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Silver",
                value = silver,
                onValueChange = { updateWealth(1, it) }
            )
        }

        item {
            WarhammerNumberField(
                label = "Gold",
                value = gold,
                onValueChange = { updateWealth(2, it) }
            )
        }
    }
}
