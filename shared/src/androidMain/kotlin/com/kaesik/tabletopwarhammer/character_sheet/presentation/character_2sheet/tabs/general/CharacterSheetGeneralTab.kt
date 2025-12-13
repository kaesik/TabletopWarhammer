package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun CharacterSheetGeneralTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
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
                    GeneralInfoHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black1)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GeneralTextField(
                            label = "Name",
                            value = character.name,
                            onValueChange = { onCharacterChange(character.copy(name = it)) }
                        )

                        GeneralTextField(
                            label = "Species",
                            value = character.species,
                            onValueChange = { onCharacterChange(character.copy(species = it)) }
                        )

                        GeneralTextField(
                            label = "Class",
                            value = character.cLass,
                            onValueChange = { onCharacterChange(character.copy(cLass = it)) }
                        )

                        GeneralTextField(
                            label = "Career",
                            value = character.career,
                            onValueChange = { onCharacterChange(character.copy(career = it)) }
                        )

                        GeneralTextField(
                            label = "Career Level",
                            value = character.careerLevel,
                            onValueChange = { onCharacterChange(character.copy(careerLevel = it)) }
                        )

                        GeneralTextField(
                            label = "Career Path",
                            value = character.careerPath,
                            onValueChange = { onCharacterChange(character.copy(careerPath = it)) }
                        )

                        GeneralTextField(
                            label = "Status",
                            value = character.status,
                            onValueChange = { onCharacterChange(character.copy(status = it)) }
                        )

                        GeneralTextField(
                            label = "Age",
                            value = character.age,
                            onValueChange = { onCharacterChange(character.copy(age = it)) }
                        )

                        GeneralTextField(
                            label = "Height",
                            value = character.height,
                            onValueChange = { onCharacterChange(character.copy(height = it)) }
                        )

                        GeneralTextField(
                            label = "Hair",
                            value = character.hair,
                            onValueChange = { onCharacterChange(character.copy(hair = it)) }
                        )

                        GeneralTextField(
                            label = "Eyes",
                            value = character.eyes,
                            onValueChange = { onCharacterChange(character.copy(eyes = it)) }
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
                    GeneralPointsHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black1)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GeneralTextField(
                            label = "Short Term Ambition",
                            value = character.ambitionShortTerm,
                            onValueChange = { onCharacterChange(character.copy(ambitionShortTerm = it)) }
                        )

                        GeneralTextField(
                            label = "Long Term Ambition",
                            value = character.ambitionLongTerm,
                            onValueChange = { onCharacterChange(character.copy(ambitionLongTerm = it)) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun GeneralInfoHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Character Information",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun GeneralPointsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Character Ambitions",
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun GeneralTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = false,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column {
        if (label.isNotBlank()) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Brown1
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = modifier
                .defaultMinSize(minHeight = 32.dp)
                .background(Brown1)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = LocalTextStyle.current.copy(
                    color = Black1,
                ),
                cursorBrush = SolidColor(Black1),
                singleLine = singleLine,
                maxLines = Int.MAX_VALUE,
                modifier = modifier
            )
        }
    }
}

@Composable
fun GeneralNumberField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = modifier
                .height(32.dp)
                .background(Brown1)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            BasicTextField(
                value = value.toString(),
                onValueChange = { text ->
                    val intValue = text.toIntOrNull() ?: 0
                    onValueChange(intValue)
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Black1,
                ),
                cursorBrush = SolidColor(Black1),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = modifier
            )
        }
    }
}

@Composable
fun GeneralInfoRow(
    label: String,
    value: String
) {
    Column {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview
@Composable
fun CharacterSheetGeneralTabPreview() {
    CharacterSheetGeneralTab(
        character = CharacterItem.default(),
        onCharacterChange = {}
    )
}
