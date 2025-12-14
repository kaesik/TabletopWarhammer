package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
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
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Light1

// TODO: CLEAN THAT THING UP LATER
@Composable
fun CharacterSheetSkillsTab(
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    SkillsHeader("Basic Skills")
                    HorizontalDivider(thickness = 1.dp, color = Brown1)
                    SkillsHeaderRow()
                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    val groupedBasic = character.basicSkills.groupBy { it[0] }
                    val basicEntries = groupedBasic.entries.toList()
                    basicEntries.forEachIndexed { index, entry ->
                        val row = buildSkillRowState(
                            character = character,
                            name = entry.key,
                            entries = entry.value,
                            isBasic = true,
                            onCharacterChange = onCharacterChange
                        )
                        SkillRow(row)
                        if (index != basicEntries.lastIndex) {
                            HorizontalDivider(thickness = 1.dp, color = Brown1)
                        }
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
                    SkillsHeader("Advanced Skills")
                    HorizontalDivider(thickness = 1.dp, color = Brown1)
                    SkillsHeaderRow()
                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    val groupedAdv = character.advancedSkills.groupBy { it[0] }
                    val advEntries = groupedAdv.entries.toList()
                    advEntries.forEachIndexed { index, entry ->
                        val row = buildSkillRowState(
                            character = character,
                            name = entry.key,
                            entries = entry.value,
                            isBasic = false,
                            onCharacterChange = onCharacterChange
                        )
                        SkillRow(row)
                        if (index != advEntries.lastIndex) {
                            HorizontalDivider(thickness = 1.dp, color = Brown1)
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(32.dp)
                            .background(Brown1)
                            .clickable {
                                val baseName = "New Advanced Skill"
                                val existingCount = character.advancedSkills.count {
                                    it
                                        .getOrNull(0)
                                        ?.startsWith(baseName) == true
                                }
                                val newName = if (existingCount == 0) {
                                    baseName
                                } else {
                                    "$baseName ${existingCount + 1}"
                                }

                                val newList = character.advancedSkills.toMutableList()
                                newList.add(listOf(newName, "Int", "0"))
                                onCharacterChange(character.copy(advancedSkills = newList))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Add advanced skill",
                            color = Black1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private data class SkillRowState(
    val name: String,
    val characteristicName: String,
    val characteristicValue: Int,
    val advances: Int,
    val skill: Int,
    val onAdvancesChange: (Int) -> Unit
)

private fun buildSkillRowState(
    character: CharacterItem,
    name: String,
    entries: List<List<String>>,
    isBasic: Boolean,
    onCharacterChange: (CharacterItem) -> Unit
): SkillRowState {
    val advances = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
    val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
    val base = getAttributeValue(character, attr)
    val total = base + advances

    val onAdvChange: (Int) -> Unit = { newAdv ->
        val value = newAdv.coerceAtLeast(0)
        if (isBasic) {
            var used = false
            val updated = character.basicSkills.map { entry ->
                if (entry.getOrNull(0) != name) entry
                else {
                    val mutable = entry.toMutableList()
                    while (mutable.size < 3) mutable.add("0")
                    val thisValue = if (!used) {
                        used = true
                        value
                    } else 0
                    mutable[2] = thisValue.toString()
                    mutable
                }
            }
            onCharacterChange(character.copy(basicSkills = updated))
        } else {
            var used = false
            val updated = character.advancedSkills.map { entry ->
                if (entry.getOrNull(0) != name) entry
                else {
                    val mutable = entry.toMutableList()
                    while (mutable.size < 3) mutable.add("0")
                    val thisValue = if (!used) {
                        used = true
                        value
                    } else 0
                    mutable[2] = thisValue.toString()
                    mutable
                }
            }
            onCharacterChange(character.copy(advancedSkills = updated))
        }
    }

    return SkillRowState(
        name = name,
        characteristicName = attr,
        characteristicValue = base,
        advances = advances,
        skill = total,
        onAdvancesChange = onAdvChange
    )
}

@Composable
private fun SkillsHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(4f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SkillsHeaderRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Name",
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Characteristic",
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Adv",
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Text(
            text = "Skill",
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun SkillRow(row: SkillRowState) {
    val showDialog = remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = row.name,
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Light1
        )

        VerticalGridDivider()

        Text(
            text = "${row.characteristicName} ${row.characteristicValue}",
            modifier = Modifier
                .weight(3f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )

        VerticalGridDivider()

        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .background(Brown1)
                .clickable { showDialog.value = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = row.advances.toString(),
                color = Black1,
                textAlign = TextAlign.Center
            )
        }

        VerticalGridDivider()

        Text(
            text = row.skill.toString(),
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Light1,
            textAlign = TextAlign.Center
        )
    }

    if (showDialog.value) {
        AdvancesDialog(
            title = row.name,
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
private fun CharacterSheetSkillsTabPreview() {
    val character = CharacterItem.default()

    CharacterSheetSkillsTab(
        character = character,
        onCharacterChange = {}
    )
}
