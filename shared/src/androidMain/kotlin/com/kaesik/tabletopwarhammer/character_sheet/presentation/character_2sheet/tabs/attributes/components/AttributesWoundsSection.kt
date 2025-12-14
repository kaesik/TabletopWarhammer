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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetStepper
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun AttributesWoundsSection(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Wounds") }
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
                    text = "SB",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "TBx2",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "WPB",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "Hardy",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )
            }

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
                CharacterSheetStepper(
                    value = currentWounds,
                    onIncrement = {
                        val newList = updateCurrentWoundsInternal(character, +1)
                        onCharacterChange(character.copy(wounds = newList))
                    },
                    onDecrement = {
                        val newList = updateCurrentWoundsInternal(character, -1)
                        onCharacterChange(character.copy(wounds = newList))
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
