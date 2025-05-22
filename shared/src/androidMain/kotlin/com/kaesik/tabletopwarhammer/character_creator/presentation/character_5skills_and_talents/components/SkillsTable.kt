package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem

@Composable
fun SkillsTable(
    skills: List<SkillItem>,
    selectedSkills3: List<SkillItem>,
    selectedSkills5: List<SkillItem>,
    speciesOrCareer: SpeciesOrCareer,
    careerSkillPoints: Map<String, Int> = emptyMap(),
    onSkillChecked3: (SkillItem, Boolean) -> Unit,
    onSkillChecked5: (SkillItem, Boolean) -> Unit,
    onCareerPointsChanged: (SkillItem, Int) -> Unit = { _, _ -> }
) {
    val limitReached3 = selectedSkills3.size >= 3
    val limitReached5 = selectedSkills5.size >= 3

    Column {
        skills.forEach { skill ->
            if (speciesOrCareer == SpeciesOrCareer.CAREER) {
                val allocated = careerSkillPoints[skill.name] ?: 0
                SkillTableItem(
                    skill = skill,
                    allocatedPoints = allocated,
                    onPointsChanged = { newValue -> onCareerPointsChanged(skill, newValue) }
                )
            } else {
                val isSelected3 = selectedSkills3.any { it.name == skill.name }
                val isSelected5 = selectedSkills5.any { it.name == skill.name }

                SkillTableItem(
                    skill = skill,
                    isSelected3 = isSelected3,
                    isSelected5 = isSelected5,
                    limitReached3 = limitReached3,
                    limitReached5 = limitReached5,
                    onCheckedChange3 = { checked ->
                        if (checked && isSelected5) onSkillChecked5(skill, false)
                        onSkillChecked3(skill, checked)
                    },
                    onCheckedChange5 = { checked ->
                        if (checked && isSelected3) onSkillChecked3(skill, false)
                        onSkillChecked5(skill, checked)
                    }
                )
            }
        }
    }
}
