package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun SkillTableItemCareer(
    skill: SkillItem,
    allocatedPoints: Int,
    onPointsChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
    compact: Boolean = false
) {
    Surface(shadowElevation = if (compact) 0.dp else 4.dp) {
        Row(
            modifier = (if (compact) modifier else modifier.fillMaxWidth())
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = if (compact) Arrangement.spacedBy(8.dp) else Arrangement.SpaceBetween
        ) {
            Text(
                text = skill.name,
                modifier = Modifier.weight(if (compact) 1f else 2f),
                maxLines = if (compact) 1 else Int.MAX_VALUE,
                overflow = TextOverflow.Ellipsis
            )

            if (!compact) {
                val openInfo = LocalOpenInfo.current
                InspectInfoIcon(
                    onClick = {
                        openInfo(
                            InspectRef(
                                type = LibraryEnum.SKILL,
                                key = skill.name
                            )
                        )
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Slider(
                    value = allocatedPoints.toFloat(),
                    onValueChange = { onPointsChanged(it.toInt().coerceIn(0, 10)) },
                    valueRange = 0f..10f,
                    steps = 9,
                    modifier = Modifier.width(if (compact) 110.dp else 150.dp)
                )
                Text("$allocatedPoints", modifier = Modifier.width(28.dp))
            }
        }
    }
}
