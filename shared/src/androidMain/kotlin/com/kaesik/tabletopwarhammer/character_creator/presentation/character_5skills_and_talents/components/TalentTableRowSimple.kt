package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun TalentTableRowSimple(
    talent: TalentItem,
    sourceLabel: String
) {
    val openInfo = LocalOpenInfo.current
    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = talent.name, modifier = Modifier.weight(2f))
            InspectInfoIcon(
                onClick = {
                    openInfo(
                        InspectRef(
                            type = LibraryEnum.TALENT,
                            key = talent.name
                        )
                    )
                }
            )
            SourceLabelBadge(sourceLabel)
        }
    }
}

@Composable
@Preview
fun TalentTableRowSimplePreview() {
    TalentTableRowSimple(
        talent = TalentItem(
            id = "1",
            name = "Test Talent",
            description = "This is a test talent.",
            source = "Core Rulebook"
        ),
        sourceLabel = "CRB"
    )
}
