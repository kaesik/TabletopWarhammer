package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SkillTableItem(
    skill: String,
) {
    Surface(
        modifier = Modifier,
        shadowElevation = 4.dp,
    ) {
        Row {
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = skill,
                )
            }
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Row {
                    Checkbox(
                        checked = false,
                        onCheckedChange = { /*TODO*/ },
                    )
                    Checkbox(
                        checked = false,
                        onCheckedChange = { /*TODO*/ },
                    )
                }
            }
        }
    }
}

@Composable
@Preview
fun SkillTableItemPreview() {
    SkillTableItem(
        skill = "Skill Name",
    )
}
