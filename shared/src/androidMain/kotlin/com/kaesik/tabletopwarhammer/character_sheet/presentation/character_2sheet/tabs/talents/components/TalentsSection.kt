package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetEmptyRow
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun TalentsSection(
    character: CharacterItem,
    onEvent: (CharacterSheetEvent) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Talents") }
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
                    text = "Name",
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )

                CharacterSheetVerticalDivider()

                Text(
                    text = "Count",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    color = Brown1,
                    textAlign = TextAlign.Center
                )
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            val groupedTalents = character.talents.groupBy { it.getOrNull(0).orEmpty() }
            val entries = groupedTalents.entries.toList()

            if (entries.isEmpty()) {
                CharacterSheetEmptyRow(text = "None")
            } else {
                entries.forEachIndexed { index, entry ->
                    val row = TalentRowState(
                        name = entry.key,
                        count = entry.value.size,
                        onCountChange = { newCount ->
                            onEvent(
                                CharacterSheetEvent.SetTalentCount(
                                    name = entry.key,
                                    count = newCount
                                )
                            )
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
                        onEvent(CharacterSheetEvent.AddTalent)
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
