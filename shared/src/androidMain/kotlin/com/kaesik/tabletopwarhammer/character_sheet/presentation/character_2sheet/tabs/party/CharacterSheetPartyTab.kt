package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components.PartyAmbitionsSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components.PartyMembersSection
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components.PartyNameSection
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

@Composable
fun CharacterSheetPartyTab(
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
            PartyNameSection(
                name = character.partyName,
                onNameChange = { newName ->
                    onCharacterChange(character.copy(partyName = newName))
                }
            )
        }

        item {
            PartyMembersSection(
                members = character.partyMembers,
                onMembersChange = { newMembers ->
                    onCharacterChange(character.copy(partyMembers = newMembers))
                }
            )
        }

        item {
            PartyAmbitionsSection(
                shortTerm = character.partyAmbitionShortTerm,
                longTerm = character.partyAmbitionLongTerm,
                onShortTermChange = { newShortTerm ->
                    onCharacterChange(character.copy(partyAmbitionShortTerm = newShortTerm))
                },
                onLongTermChange = { newLongTerm ->
                    onCharacterChange(character.copy(partyAmbitionLongTerm = newLongTerm))
                },
            )
        }
    }
}

@Preview
@Composable
fun CharacterSheetPartyTabPreview() {
    val character = CharacterItem.default().copy(
        partyName = "The Brave Companions",
        partyMembers = listOf(
            listOf("Aric", "Leader of the group"),
            listOf("Borin", "Skilled warrior"),
            listOf("Lina", "Healer and mage")
        )
    )

    CharacterSheetPartyTab(
        character = character,
        onCharacterChange = {}
    )
}
