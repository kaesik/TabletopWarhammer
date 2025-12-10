package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

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
                        GeneralNumberField(
                            label = "Fate",
                            value = character.fate,
                            onValueChange = { onCharacterChange(character.copy(fate = it)) }
                        )

                        GeneralNumberField(
                            label = "Fortune",
                            value = character.fortune,
                            onValueChange = { onCharacterChange(character.copy(fortune = it)) }
                        )

                        GeneralNumberField(
                            label = "Resilience",
                            value = character.resilience,
                            onValueChange = { onCharacterChange(character.copy(resilience = it)) }
                        )

                        GeneralNumberField(
                            label = "Resolve",
                            value = character.resolve,
                            onValueChange = { onCharacterChange(character.copy(resolve = it)) }
                        )

                        GeneralTextField(
                            label = "Motivation",
                            value = character.motivation,
                            onValueChange = { onCharacterChange(character.copy(motivation = it)) }
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
            text = "Points and Motivation",
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
    singleLine: Boolean = true,
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

        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier.height(32.dp),
            singleLine = singleLine,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Brown1,
                unfocusedContainerColor = Brown1,
                focusedTextColor = Black1,
                unfocusedTextColor = Black1,
            )
        )
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

        TextField(
            value = value.toString(),
            onValueChange = { text ->
                val intValue = text.toIntOrNull() ?: 0
                onValueChange(intValue)
            },
            modifier = modifier,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Brown1,
                unfocusedContainerColor = Brown1,
                focusedTextColor = Black1,
                unfocusedTextColor = Black1,
            )
        )
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
