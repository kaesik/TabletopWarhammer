package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTableHeaderCell
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1


@Composable
fun PointsExperienceSection(
    current: Int,
    spent: Int,
    total: Int,
    onCurrentChange: (Int) -> Unit,
    onSpentChange: (Int) -> Unit,
    onTotalChange: (Int) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Experience Points") }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterSheetTableHeaderCell(text = "Current")

                CharacterSheetVerticalDivider()

                CharacterSheetTableHeaderCell(text = "Spent")

                CharacterSheetVerticalDivider()

                CharacterSheetTableHeaderCell(text = "Total")
            }

            HorizontalDivider(thickness = 1.dp, color = Brown1)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Brown1)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = current.toString(),
                        onValueChange = { text ->
                            onCurrentChange(text.toIntOrNull() ?: 0)
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

                CharacterSheetVerticalDivider()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Brown1)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = spent.toString(),
                        onValueChange = { text ->
                            onSpentChange(text.toIntOrNull() ?: 0)
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

                CharacterSheetVerticalDivider()

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(Brown1)
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = total.toString(),
                        onValueChange = { text ->
                            onTotalChange(text.toIntOrNull() ?: 0)
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
        }
    }
}
