package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

@Composable
fun TalentTableItem(
    talent: TalentItem,
    isSelected: Boolean,
    sourceLabel: String,
    onTalentChecked: (TalentItem, Boolean) -> Unit,
    onRandomTalentRoll: (TalentItem) -> Unit = {}
) {
    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = talent.name,
                modifier = Modifier.weight(2f)
            )

            when {
                talent.name == "Random Talent" -> {
                    IconButton(onClick = { onRandomTalentRoll(talent) }) {
                        Icon(
                            imageVector = Icons.Default.Casino,
                            contentDescription = "Roll Random Talent"
                        )
                    }
                }

                " or " in talent.name -> {
                    Checkbox(
                        checked = isSelected,
                        onCheckedChange = { isChecked -> onTalentChecked(talent, isChecked) }
                    )
                }

                else -> {
                    SourceLabelBadge(sourceLabel)
                }
            }
        }
    }
}

@Composable
@Preview
fun TalentTableItemPreview() {
    TalentTableItem(
        talent = TalentItem(name = "Random Talent", id = ""),
        isSelected = false,
        sourceLabel = "Career",
        onTalentChecked = { _, _ -> },
        onRandomTalentRoll = { println("Roll talent!") }
    )
}
