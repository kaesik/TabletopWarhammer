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
fun GeneralAmbitionsSection(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Character Ambitions") }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Black1)
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CharacterSheetTextField(
                label = "Short Term Ambition",
                value = character.ambitionShortTerm,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            ambitionShortTerm = it
                        )
                    )
                }
            )
            CharacterSheetTextField(
                label = "Long Term Ambition",
                value = character.ambitionLongTerm,
                onValueChange = {
                    onEvent(
                        CharacterSheetEvent.SetGeneralInfo(
                            ambitionLongTerm = it
                        )
                    )
                }
            )
        }
    }
}
