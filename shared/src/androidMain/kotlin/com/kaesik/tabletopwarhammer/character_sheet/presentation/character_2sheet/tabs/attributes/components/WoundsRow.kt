package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetVerticalDivider
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun WoundsRow(character: CharacterItem) {
    val strengthCurrent = character.strength.getOrNull(2) ?: 0
    val toughnessCurrent = character.toughness.getOrNull(2) ?: 0
    val willpowerCurrent = character.willPower.getOrNull(2) ?: 0

    fun bonus(value: Int) = (value / 10).coerceAtLeast(0)

    val sb = bonus(strengthCurrent)
    val tbx2 = bonus(toughnessCurrent) * 2
    val wpb = bonus(willpowerCurrent)
    val hardy = 1

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = sb.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )

        CharacterSheetVerticalDivider()

        Text(
            text = tbx2.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )

        CharacterSheetVerticalDivider()

        Text(
            text = wpb.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )

        CharacterSheetVerticalDivider()

        Text(
            text = hardy.toString(),
            color = Brown1,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 8.dp),
            textAlign = TextAlign.Center
        )
    }
}
