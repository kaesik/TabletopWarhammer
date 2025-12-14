package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTextField
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1

@Composable
fun GeneralInfoSection(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Character Information") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black1)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterSheetTextField(
                label = "Name",
                value = character.name,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            name = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Species",
                value = character.species,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            species = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Class",
                value = character.cLass,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            cLass = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Career",
                value = character.career,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            career = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Career Level",
                value = character.careerLevel,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            careerLevel = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Career Path",
                value = character.careerPath,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            careerPath = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Status",
                value = character.status,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            status = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Age",
                value = character.age,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            age = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Height",
                value = character.height,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            height = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Hair",
                value = character.hair,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            hair = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Eyes",
                value = character.eyes,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            eyes = it
                        )
                    )
                }
            )
        }
    }
}
