package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem


fun mapSpecializedSkills(skillLists: List<List<SkillItem>>): List<List<SkillItem>> {
    return skillLists.map { list ->
        list.map { skill ->
            if ("(" in skill.name && skill.name.contains(")")) {
                val baseName = skill.name.substringBefore(" (").trim()
                val specialization = skill.name.substringAfter(" (").removeSuffix(")").trim()

                skill.copy(
                    name = "$baseName ($specialization)",
                    isBasic = false,
                    specialization = specialization
                )
            } else {
                skill
            }
        }
    }
}
