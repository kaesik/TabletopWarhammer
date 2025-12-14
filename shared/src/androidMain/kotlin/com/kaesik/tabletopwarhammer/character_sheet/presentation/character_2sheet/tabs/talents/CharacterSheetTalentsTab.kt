package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents

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
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Light1

@Composable
fun CharacterSheetTalentsTab(
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
                    TalentsHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    TalentsHeaderRow()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    val groupedTalents = character.talents.groupBy { it.getOrNull(0).orEmpty() }
                    val entries = groupedTalents.entries.toList()

                    if (entries.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .background(Black1),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "None",
                                modifier = Modifier
                                    .weight(2f)
                                    .padding(horizontal = 8.dp),
                                color = Brown1
                            )
                        }
                    } else {
                        entries.forEachIndexed { index, entry ->
                            val row = TalentRowState(
                                name = entry.key,
                                count = entry.value.size,
                                onCountChange = { newCount ->
                                    val updated = updateTalentCount(
                                        character = character,
                                        name = entry.key,
                                        newCount = newCount
                                    )
                                    onCharacterChange(character.copy(talents = updated))
                                }
                            )
                            TalentRow(row)
                            if (index != entries.lastIndex) {
                                HorizontalDivider(thickness = 1.dp, color = Brown1)
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .height(32.dp)
                            .background(Brown1)
                            .clickable {
                                val newList = character.talents.toMutableList()
                                val newIndex = character.talents.size + 1
                                newList.add(listOf("New Talent $newIndex"))
                                onCharacterChange(character.copy(talents = newList))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Add talent",
                            color = Black1,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

private data class TalentRowState(
    val name: String,
    val count: Int,
    val onCountChange: (Int) -> Unit
)

@Composable
private fun TalentsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Talents",
            modifier = Modifier
                .weight(2f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TalentsHeaderRow() {
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
            text = "Count",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun TalentRow(row: TalentRowState) {
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

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Brown1)
                .clickable { showDialog.value = true },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = row.count.toString(),
                color = Black1,
                textAlign = TextAlign.Center
            )
        }

        if (showDialog.value) {
            TalentsCountDialog(
                title = row.name,
                currentCount = row.count,
                onDismiss = { showDialog.value = false },
                onConfirm = { newCount ->
                    showDialog.value = false
                    row.onCountChange(newCount)
                }
            )
        }
    }
}

@Composable
private fun TalentsCountDialog(
    title: String,
    currentCount: Int,
    onDismiss: () -> Unit,
    onConfirm: (Int) -> Unit
) {
    val tempCount = remember { mutableIntStateOf(currentCount.coerceAtLeast(0)) }

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
                    value = tempCount.intValue,
                    onIncrement = { tempCount.intValue += 1 },
                    onDecrement = { tempCount.intValue = (tempCount.intValue - 1).coerceAtLeast(0) }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempCount.intValue) }) {
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

private fun updateTalentCount(
    character: CharacterItem,
    name: String,
    newCount: Int
): List<List<String>> {
    val baseList = character.talents
    val count = newCount.coerceAtLeast(0)
    val template = baseList.firstOrNull { it.getOrNull(0) == name } ?: listOf(name)

    val result = mutableListOf<List<String>>()
    var inserted = false

    for (entry in baseList) {
        if (entry.getOrNull(0) == name) {
            if (!inserted) {
                repeat(count) {
                    result.add(template)
                }
                inserted = true
            }
        } else {
            result.add(entry)
        }
    }

    if (!inserted && count > 0) {
        repeat(count) {
            result.add(template)
        }
    }

    return result
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
private fun CharacterSheetTalentsTabPreview() {
    val character = CharacterItem.default().copy(
        talents = listOf(
            listOf("Brave"),
            listOf("Strong"),
            listOf("Brave"),
            listOf("Agile"),
        )
    )
    CharacterSheetTalentsTab(
        character = character,
        onCharacterChange = {}
    )
}
