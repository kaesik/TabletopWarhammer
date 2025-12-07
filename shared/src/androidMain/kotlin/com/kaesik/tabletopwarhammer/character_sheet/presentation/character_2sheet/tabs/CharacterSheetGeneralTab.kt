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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerTextField
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

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
        item { WarhammerSectionTitle("General Information") }

        item {
            WarhammerTextField(
                label = "Name",
                value = character.name,
                onValueChange = { onCharacterChange(character.copy(name = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Species",
                value = character.species,
                onValueChange = { onCharacterChange(character.copy(species = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Class",
                value = character.cLass,
                onValueChange = { onCharacterChange(character.copy(cLass = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Career",
                value = character.career,
                onValueChange = { onCharacterChange(character.copy(career = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Career Level",
                value = character.careerLevel,
                onValueChange = { onCharacterChange(character.copy(careerLevel = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Career Path",
                value = character.careerPath,
                onValueChange = { onCharacterChange(character.copy(careerPath = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Status",
                value = character.status,
                onValueChange = { onCharacterChange(character.copy(status = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Age",
                value = character.age,
                onValueChange = { onCharacterChange(character.copy(age = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Height",
                value = character.height,
                onValueChange = { onCharacterChange(character.copy(height = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Hair",
                value = character.hair,
                onValueChange = { onCharacterChange(character.copy(hair = it)) }
            )
        }

        item {
            WarhammerTextField(
                label = "Eyes",
                value = character.eyes,
                onValueChange = { onCharacterChange(character.copy(eyes = it)) }
            )
        }

        item { WarhammerSectionTitle("Points and Motivation") }

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

        item { WarhammerSectionTitle("Experience") }
        item {
            WarhammerInfoRow(
                label = "Current / Spent / Total",
                value = character.experience.joinToString(" / ")
            )
        }
    }
}
