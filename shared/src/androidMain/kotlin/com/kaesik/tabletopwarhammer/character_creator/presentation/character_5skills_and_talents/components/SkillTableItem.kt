package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun SkillTableItem(
    skill: SkillItem,
    isSelected3: Boolean = false,
    isSelected5: Boolean = false,
    limitReached3: Boolean = false,
    limitReached5: Boolean = false,
    allocatedPoints: Int? = null,
    onCheckedChange3: ((Boolean) -> Unit)? = null,
    onCheckedChange5: ((Boolean) -> Unit)? = null,
    onPointsChanged: ((Int) -> Unit)? = null
) {
    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = skill.name,
                modifier = Modifier.weight(2f)
            )

            // Info icon to inspect the species details
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

            if (onPointsChanged != null && allocatedPoints != null) {
                // CAREER MODE
                Slider(
                    value = allocatedPoints.toFloat(),
                    onValueChange = { onPointsChanged(it.toInt()) },
                    valueRange = 0f..10f,
                    steps = 9,
                    modifier = Modifier.width(150.dp)
                )
                Text("$allocatedPoints", modifier = Modifier.width(32.dp))
            } else {
                // SPECIES MODE
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isSelected3,
                        onCheckedChange = onCheckedChange3,
                        enabled = isSelected3 || !limitReached3
                    )
                    Text("+3")
                    Checkbox(
                        checked = isSelected5,
                        onCheckedChange = onCheckedChange5,
                        enabled = isSelected5 || !limitReached5
                    )
                    Text("+5")
                }
            }
        }
    }
}
