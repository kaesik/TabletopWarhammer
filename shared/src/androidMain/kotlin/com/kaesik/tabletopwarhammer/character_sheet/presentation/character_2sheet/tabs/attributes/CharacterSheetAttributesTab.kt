package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults.iconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Light1
import com.kaesik.tabletopwarhammer.core.theme.Red

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetAttributesTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    println(character)
    val attributes = remember(character) {
        listOf(
            AttributeRowState(
                shortLabel = "WS",
                fullLabel = "Weapon Skill",
                base = character.weaponSkill[0],
                advances = character.weaponSkill[1],
                current = character.weaponSkill[2]
            ) { newAdv ->
                val base = character.weaponSkill[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(weaponSkill = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "BS",
                fullLabel = "Ballistic Skill",
                base = character.ballisticSkill[0],
                advances = character.ballisticSkill[1],
                current = character.ballisticSkill[2]
            ) { newAdv ->
                val base = character.ballisticSkill[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(ballisticSkill = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "S",
                fullLabel = "Strength",
                base = character.strength[0],
                advances = character.strength[1],
                current = character.strength[2]
            ) { newAdv ->
                val base = character.strength[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(strength = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "T",
                fullLabel = "Toughness",
                base = character.toughness[0],
                advances = character.toughness[1],
                current = character.toughness[2]
            ) { newAdv ->
                val base = character.toughness[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(toughness = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "I",
                fullLabel = "Initiative",
                base = character.initiative[0],
                advances = character.initiative[1],
                current = character.initiative[2]
            ) { newAdv ->
                val base = character.initiative[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(initiative = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "Ag",
                fullLabel = "Agility",
                base = character.agility[0],
                advances = character.agility[1],
                current = character.agility[2]
            ) { newAdv ->
                val base = character.agility[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(agility = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "Dex",
                fullLabel = "Dexterity",
                base = character.dexterity[0],
                advances = character.dexterity[1],
                current = character.dexterity[2]
            ) { newAdv ->
                val base = character.dexterity[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(dexterity = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "Int",
                fullLabel = "Intelligence",
                base = character.intelligence[0],
                advances = character.intelligence[1],
                current = character.intelligence[2]
            ) { newAdv ->
                val base = character.intelligence[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(intelligence = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "WP",
                fullLabel = "Willpower",
                base = character.willPower[0],
                advances = character.willPower[1],
                current = character.willPower[2]
            ) { newAdv ->
                val base = character.willPower[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(willPower = listOf(base, adv, base + adv)))
            },
            AttributeRowState(
                shortLabel = "Fel",
                fullLabel = "Fellowship",
                base = character.fellowship[0],
                advances = character.fellowship[1],
                current = character.fellowship[2]
            ) { newAdv ->
                val base = character.fellowship[0]
                val adv = newAdv.coerceAtLeast(0)
                onCharacterChange(character.copy(fellowship = listOf(base, adv, base + adv)))
            }
        )
    }

    fun updateCurrentWounds(
        character: CharacterItem,
        delta: Int,
        onCharacterChange: (CharacterItem) -> Unit
    ) {
        val list = character.wounds.toMutableList()
        while (list.size < 2) list.add(0)

        val maxWounds = list[1].coerceAtLeast(0)
        if (maxWounds <= 0) return

        val storedCurrent = list[0]
        val effectiveCurrent = if (storedCurrent <= 0) maxWounds else storedCurrent

        val newCurrent = (effectiveCurrent + delta).coerceIn(0, maxWounds)

        list[0] = newCurrent

        onCharacterChange(character.copy(wounds = list))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    WoundsHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    WoundsHeaderRow()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    WoundsRow(character)

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    val maxWounds = character.wounds.getOrNull(1) ?: 0
                    val storedCurrent = character.wounds.getOrNull(0) ?: 0
                    val currentWounds =
                        if (storedCurrent <= 0 && maxWounds > 0) maxWounds else storedCurrent

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PointsStepperCell(
                            value = currentWounds,
                            onIncrement = {
                                updateCurrentWounds(character, +1, onCharacterChange)
                            },
                            onDecrement = {
                                updateCurrentWounds(character, -1, onCharacterChange)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        Text(
                            text = "/ $maxWounds",
                            color = Brown1,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }

                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    AttributeHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    AttributeHeaderRow()

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
    }
}

@Composable
private fun WoundsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Wounds",
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WoundsHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "SB",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "TBx2",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "WPB",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Hardy",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun WoundsRow(character: CharacterItem) {
    val strengthCurrent = character.strength.getOrNull(2) ?: 0
    val toughnessCurrent = character.toughness.getOrNull(2) ?: 0
    val willpowerCurrent = character.willPower.getOrNull(2) ?: 0

    fun bonus(value: Int) = (value / 10).coerceAtLeast(0)

    val sb = bonus(strengthCurrent)
    val tbx2 = bonus(toughnessCurrent) * 2
    val wpb = bonus(willpowerCurrent)
    val hardy = 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sb.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )

        VerticalGridDivider()

        Text(
            text = tbx2.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )

        VerticalGridDivider()

        Text(
            text = wpb.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )

        VerticalGridDivider()

        Text(
            text = hardy.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )
    }
}

private data class AttributeRowState(
    val shortLabel: String,
    val fullLabel: String,
    val base: Int,
    val advances: Int,
    val current: Int,
    val onAdvancesChange: (Int) -> Unit
)

@Composable
private fun PointsStepperCell(
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxHeight()
            .background(Brown1)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "<",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable { onDecrement() },
            color = Black1,
            textAlign = TextAlign.Center
        )
        Text(
            text = value.toString(),
            modifier = Modifier.padding(horizontal = 4.dp),
            color = Black1,
            textAlign = TextAlign.Center
        )
        Text(
            text = ">",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .clickable { onIncrement() },
            color = Black1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AttributeHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Characteristics",
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AttributeHeaderRow() {
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

        VerticalGridDivider()

        Text(
            text = "Initial",
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Adv",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Current",
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun AttributeRow(
    row: AttributeRowState,
    upgradable: Boolean = true
) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(4f)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = row.shortLabel,
                color = Light1,
                modifier = Modifier.padding(end = 8.dp)
            )
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = row.fullLabel,
                tint = Red,
                modifier = Modifier.size(16.dp)
            )
        }

        VerticalGridDivider()

        Text(
            text = row.base.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center,
        )

        VerticalGridDivider()

        IconButton(
            onClick = { if (upgradable) showDialog.value = true },
            colors = iconButtonColors(
                containerColor = if (upgradable) Brown1 else Black1
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) {
            Text(
                text = row.advances.toString(),
                color = if (upgradable) Black1 else Brown1,
            )
        }

        VerticalGridDivider()

        Text(
            text = row.current.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Light1,
            textAlign = TextAlign.Center
        )
    }

    if (showDialog.value) {
        AdvancesDialog(
            title = row.fullLabel,
            currentAdvances = row.advances,
            onDismiss = { showDialog.value = false },
            onConfirm = { newAdv ->
                showDialog.value = false
                row.onAdvancesChange(newAdv)
            }
        )
    }
}

@Composable
private fun AdvancesDialog(
    title: String,
    currentAdvances: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val tempAdv = remember { mutableIntStateOf(currentAdvances.coerceAtLeast(0)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                PointsStepperCell(
                    value = tempAdv.intValue,
                    onIncrement = { tempAdv.intValue += 1 },
                    onDecrement = { tempAdv.intValue = (tempAdv.intValue - 1).coerceAtLeast(0) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempAdv.intValue) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
private fun VerticalGridDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Brown1)
    )
}

@Preview
@Composable
private fun CharacterSheetAttributesTabPreview() {
    CharacterSheetAttributesTab(
        character = CharacterItem.default(),
        onCharacterChange = {}
    )
}
