package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.points.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetTableHeaderCell
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun PointsResilienceSection(
    resilience: Int,
    resolve: Int,
    motivation: String,
    onResilienceChange: (Int) -> Unit,
    onResolveChange: (Int) -> Unit,
    onMotivationChange: (String) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Resilience Points") }
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
                    .background(Black1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterSheetTableHeaderCell(text = "Resilience")
                CharacterSheetVerticalDivider()
                CharacterSheetTableHeaderCell(text = "Resolve")
                CharacterSheetVerticalDivider()
                CharacterSheetTableHeaderCell(text = "Motivation")
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
                        value = resilience.toString(),
                        onValueChange = { text ->
                            onResilienceChange(text.toIntOrNull() ?: 0)
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
                        value = resolve.toString(),
                        onValueChange = { text ->
                            onResolveChange(text.toIntOrNull() ?: 0)
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
                        value = motivation,
                        onValueChange = onMotivationChange,
                        textStyle = LocalTextStyle.current.copy(
                            color = Black1,
                            textAlign = TextAlign.Center
                        ),
                        cursorBrush = SolidColor(Black1),
                        singleLine = false,
                        maxLines = 2,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}
