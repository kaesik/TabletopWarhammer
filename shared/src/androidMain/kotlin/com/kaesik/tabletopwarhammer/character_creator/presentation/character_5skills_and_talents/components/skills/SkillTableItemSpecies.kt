package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.skills

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
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
fun SkillTableItemSpecies(
    skill: SkillItem,
    isSelected3: Boolean,
    isSelected5: Boolean,
    limitReached3: Boolean,
    limitReached5: Boolean,
    onCheckedChange3: (Boolean) -> Unit,
    onCheckedChange5: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = skill.name,
                modifier = Modifier.weight(2f)
            )

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
