package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun AttributesCharacteristicsSection(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit,
) {
    val attributes = remember(character) {
        listOf(
            AttributeRowState(
                shortLabel = "WS",
                fullLabel = "Weapon Skill",
                base = character.weaponSkill[0],
                advances = character.weaponSkill[1],
                current = character.weaponSkill[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.WS,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "BS",
                fullLabel = "Ballistic Skill",
                base = character.ballisticSkill[0],
                advances = character.ballisticSkill[1],
                current = character.ballisticSkill[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.BS,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "S",
                fullLabel = "Strength",
                base = character.strength[0],
                advances = character.strength[1],
                current = character.strength[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.S,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "T",
                fullLabel = "Toughness",
                base = character.toughness[0],
                advances = character.toughness[1],
                current = character.toughness[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.T,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "I",
                fullLabel = "Initiative",
                base = character.initiative[0],
                advances = character.initiative[1],
                current = character.initiative[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.I,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "Ag",
                fullLabel = "Agility",
                base = character.agility[0],
                advances = character.agility[1],
                current = character.agility[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.Ag,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "Dex",
                fullLabel = "Dexterity",
                base = character.dexterity[0],
                advances = character.dexterity[1],
                current = character.dexterity[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.Dex,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "Int",
                fullLabel = "Intelligence",
                base = character.intelligence[0],
                advances = character.intelligence[1],
                current = character.intelligence[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.Int,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "WP",
                fullLabel = "Willpower",
                base = character.willPower[0],
                advances = character.willPower[1],
                current = character.willPower[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.WP,
                        advances = newAdv
                    )
                )
            },
            AttributeRowState(
                shortLabel = "Fel",
                fullLabel = "Fellowship",
                base = character.fellowship[0],
                advances = character.fellowship[1],
                current = character.fellowship[2]
            ) { newAdv ->
                onEvent(
                    CharacterSheetEvent.SetAttributeAdvances(
                        attribute = CharacterSheetEvent.Attribute.Fel,
                        advances = newAdv
                    )
                )
            }
        )
    }

    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Characteristics") }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attributes",
                    modifier = Modifier
                        .weight(4f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "Initial",
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "Adv",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "Current",
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            attributes.forEachIndexed { index, row ->
                AttributeRow(row)
                if (index != attributes.lastIndex) {
                    HorizontalDivider(thickness = 1.dp, color = Brown1)
                }
            }
        }
    }
}
