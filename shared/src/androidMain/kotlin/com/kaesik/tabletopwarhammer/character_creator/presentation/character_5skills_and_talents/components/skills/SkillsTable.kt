package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.skills

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SeparatorOr
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.baseOf
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.canon
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
    onCareerPointsChanged: (SkillItem, Int) -> Unit = { _, _ -> },
    skillOrGroups: Map<String, Set<String>> = emptyMap()
) {
    val limitReached3 = selectedSkills3.size >= 3
    val limitReached5 = selectedSkills5.size >= 3

    val canonSkills = remember(skills) { skills.map { it.copy(name = canon(it.name)) } }
    val groups = remember(canonSkills) {
        canonSkills
            .groupBy { baseOf(it.name) }
            .values
            .map { it.sortedBy { s -> s.name } }
            .toList()
    }

    fun shouldRenderAsOr(base: String, variants: List<SkillItem>): Boolean {
        val set = skillOrGroups[base] ?: return false
        return variants.isNotEmpty() && variants.all { canon(it.name) in set }
    }

    Column {
        groups.forEach { group ->
            val base = baseOf(group.first().name)
            val renderAsOr = group.size > 1 && shouldRenderAsOr(base, group)

            when (speciesOrCareer) {
                SpeciesOrCareer.SPECIES -> {
                    group.forEach { skill ->
                        val isSelected3 = selectedSkills3.any { it.name == skill.name }
                        val isSelected5 = selectedSkills5.any { it.name == skill.name }
                        SkillTableItemSpecies(
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

                SpeciesOrCareer.CAREER -> {
                    if (!renderAsOr) {
                        group.forEach { skill ->
                            val allocated = careerSkillPoints[skill.name] ?: 0
                            SkillTableItemCareer(
                                skill = skill,
                                allocatedPoints = allocated,
                                onPointsChanged = { newValue ->
                                    onCareerPointsChanged(skill, newValue)
                                }
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            group.forEachIndexed { idx, skill ->
                                val allocated = careerSkillPoints[skill.name] ?: 0
                                SkillTableItemCareer(
                                    skill = skill,
                                    allocatedPoints = allocated,
                                    onPointsChanged = { newValue ->
                                        if (newValue > 0) {
                                            group.forEach { other ->
                                                if (other.name != skill.name &&
                                                    (careerSkillPoints[other.name] ?: 0) != 0
                                                ) onCareerPointsChanged(other, 0)
                                            }
                                        }
                                        onCareerPointsChanged(skill, newValue)
                                    }
                                )
                                if (idx < group.lastIndex) SeparatorOr()
                            }
                        }
                    }
                }
            }
        }
    }
}
