package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

// TODO: CLEAN THAT THING UP LATER
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

    fun addExperience(delta: Int) {
        val list = character.experience.toMutableList()
        while (list.size < 3) list.add(0)

        val newCurrent = (list[0] + delta).coerceAtLeast(0)
        val newTotal = (list[2] + delta).coerceAtLeast(0)

        list[0] = newCurrent
        list[2] = newTotal

        onCharacterChange(character.copy(experience = list))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    PointsHeader(title = "Add Points")

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderCell(text = "Experience")
                        VerticalGridDivider()
                        HeaderCell(text = "Resilience")
                        VerticalGridDivider()
                        HeaderCell(text = "Fate")
                    }

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PointsStepperCell(
                            value = currentXp,
                            onIncrement = {
                                addExperience(1)
                            },
                            onDecrement = {
                                addExperience(-1)
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsStepperCell(
                            value = character.resilience,
                            onIncrement = {
                                onCharacterChange(character.copy(resilience = character.resilience + 1))
                            },
                            onDecrement = {
                                onCharacterChange(
                                    character.copy(
                                        resilience = (character.resilience - 1).coerceAtLeast(
                                            0
                                        )
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsStepperCell(
                            value = character.fate,
                            onIncrement = {
                                onCharacterChange(character.copy(fate = character.fate + 1))
                            },
                            onDecrement = {
                                onCharacterChange(
                                    character.copy(
                                        fate = (character.fate - 1).coerceAtLeast(
                                            0
                                        )
                                    )
                                )
                            },
                            modifier = Modifier.weight(1f)
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
                    PointsHeader(title = "Experience Points")

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderCell(text = "Current")
                        VerticalGridDivider()
                        HeaderCell(text = "Spent")
                        VerticalGridDivider()
                        HeaderCell(text = "Total")
                    }

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PointsNumberCell(
                            value = currentXp,
                            onValueChange = { updateXp(0, it) },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsNumberCell(
                            value = spentXp,
                            onValueChange = { updateXp(1, it) },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsNumberCell(
                            value = totalXp,
                            onValueChange = { updateXp(2, it) },
                            modifier = Modifier.weight(1f)
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
                    PointsHeader(title = "Resilience Points")

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderCell(text = "Resilience")
                        VerticalGridDivider()
                        HeaderCell(text = "Resolve")
                        VerticalGridDivider()
                        HeaderCell(text = "Motivation")
                    }

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PointsNumberCell(
                            value = character.resilience,
                            onValueChange = {
                                onCharacterChange(character.copy(resilience = it))
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsNumberCell(
                            value = character.resolve,
                            onValueChange = {
                                onCharacterChange(character.copy(resolve = it))
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsTextCell(
                            value = character.motivation,
                            onValueChange = {
                                onCharacterChange(character.copy(motivation = it))
                            },
                            modifier = Modifier.weight(1f)
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
                    PointsHeader(title = "Fate Points")

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderCell(text = "Fate", weight = 1f)
                        VerticalGridDivider()
                        HeaderCell(text = "Fortune", weight = 1f)
                    }

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PointsNumberCell(
                            value = character.fate,
                            onValueChange = {
                                onCharacterChange(character.copy(fate = it))
                            },
                            modifier = Modifier.weight(1f)
                        )

                        VerticalGridDivider()

                        PointsNumberCell(
                            value = character.fortune,
                            onValueChange = {
                                onCharacterChange(character.copy(fortune = it))
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }

        item {
            CorruptionAndMutationsSection(
                corruption = character.corruption,
                mutations = character.mutations,
                onCorruptionChange = { newValue ->
                    onCharacterChange(character.copy(corruption = newValue))
                },
                onMutationsChange = { newList ->
                    onCharacterChange(character.copy(mutations = newList))
                }
            )
        }

    }
}

@Composable
private fun PointsHeader(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RowScope.HeaderCell(
    text: String,
    weight: Float = 1f,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .weight(weight),
        color = Brown1,
        textAlign = TextAlign.Center,
    )
}

@Composable
private fun PointsNumberCell(
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Brown1)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value.toString(),
            onValueChange = { text ->
                val intValue = text.toIntOrNull() ?: 0
                onValueChange(intValue)
            },
            textStyle = LocalTextStyle.current.copy(
                color = Black1,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Black1),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PointsTextCell(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Brown1)
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = LocalTextStyle.current.copy(
                color = Black1,
                textAlign = TextAlign.Center
            ),
            cursorBrush = SolidColor(Black1),
            singleLine = false,
            maxLines = 2,
            modifier = Modifier.fillMaxWidth()
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
                .clickable { onDecrement() }
                .padding(horizontal = 4.dp),
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
                .clickable { onIncrement() }
                .padding(horizontal = 4.dp),
            color = Black1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun CorruptionAndMutationsSection(
    corruption: Int,
    mutations: List<String>,
    onCorruptionChange: (Int) -> Unit,
    onMutationsChange: (List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, Brown1)
    ) {
        Column {
            PointsHeader(title = "Corruption")

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Corruption Points",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                PointsStepperCell(
                    value = corruption,
                    onIncrement = { onCorruptionChange(corruption + 1) },
                    onDecrement = { onCorruptionChange((corruption - 1).coerceAtLeast(0)) },
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(32.dp)
                            .background(Black1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HeaderCell(text = "Mutation")
                        VerticalGridDivider()
                        HeaderCell(text = "Description")
                    }

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    if (mutations.isEmpty()) {
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
                        mutations.forEachIndexed { index, mutation ->
                            val parts = mutation.split(":", limit = 2)
                            val name = parts.getOrNull(0).orEmpty()
                            val effect = parts.getOrNull(1).orEmpty()

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(32.dp)
                                    .background(Black1),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = name,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    color = Brown1
                                )

                                VerticalGridDivider()

                                Text(
                                    text = effect,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    color = Brown1
                                )
                            }

                            if (index != mutations.lastIndex) {
                                HorizontalDivider(thickness = 1.dp, color = Brown1)
                            }
                        }
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
                        val newList = mutations.toMutableList()
                        newList.add("Mutation:Description")
                        onMutationsChange(newList)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add mutation",
                    color = Black1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
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
private fun CharacterSheetPointsTabPreview() {
    CharacterSheetPointsTab(
        character = CharacterItem.default(),
        onCharacterChange = {}
    )
}
