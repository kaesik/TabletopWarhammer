package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components

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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetEmptyRow
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTableHeaderCell
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun PointsMutationsSection(
    mutations: List<String>,
    onAddMutation: () -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Mutations") }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterSheetTableHeaderCell(text = "Mutation")
                CharacterSheetVerticalDivider()
                CharacterSheetTableHeaderCell(text = "Description")
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            if (mutations.isEmpty()) {
                CharacterSheetEmptyRow(text = "None")
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
                        CharacterSheetVerticalDivider()
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

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(32.dp)
                    .background(Brown1)
                    .clickable {
                        onAddMutation()
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
