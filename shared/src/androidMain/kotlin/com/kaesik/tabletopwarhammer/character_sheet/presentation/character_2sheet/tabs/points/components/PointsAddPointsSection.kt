package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun PointsAddPointsSection(
    currentXp: Int,
    resilience: Int,
    fate: Int,
    onAddExperience: (Int) -> Unit,
    onResilienceChange: (Int) -> Unit,
    onFateChange: (Int) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Add Points") }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Experience",
                        color = Brown1,
                        textAlign = TextAlign.Center
                    )
                }
                CharacterSheetVerticalDivider()
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Resilience",
                        color = Brown1,
                        textAlign = TextAlign.Center
                    )
                }
                CharacterSheetVerticalDivider()
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Fate",
                        color = Brown1,
                        textAlign = TextAlign.Center
                    )
                }
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterSheetStepper(
                    value = currentXp,
                    onIncrement = { onAddExperience(1) },
                    onDecrement = { onAddExperience(-1) },
                    modifier = Modifier.weight(1f)
                )
                CharacterSheetVerticalDivider()
                CharacterSheetStepper(
                    value = resilience,
                    onIncrement = { onResilienceChange(resilience + 1) },
                    onDecrement = { onResilienceChange(resilience - 1) },
                    modifier = Modifier.weight(1f)
                )
                CharacterSheetVerticalDivider()
                CharacterSheetStepper(
                    value = fate,
                    onIncrement = { onFateChange(fate + 1) },
                    onDecrement = { onFateChange(fate - 1) },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
