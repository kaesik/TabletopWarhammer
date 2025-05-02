package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem

@Composable
fun SkillsTable(
    skills: List<SkillItem>,
    selectedSkills: List<SkillItem>,
    onSkillChecked: (SkillItem, Boolean) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .height(300.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(skills) { skill ->
            SkillTableItem(
                skill = skill,
                isSelected = selectedSkills.contains(skill),
                onCheckedChange = { isChecked ->
                    onSkillChecked(skill, isChecked)
                }
            )
        }
    }
}
