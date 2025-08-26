package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem


fun mapSpecializedSkills(skillLists: List<List<SkillItem>>): List<List<SkillItem>> {
    return skillLists.map { list ->
        list.map { skill ->
            val match = Regex("""^(.*) \((.*)\)$""").matchEntire(skill.name)
            if (match != null) {
                val (baseName, specialization) = match.destructured
                val baseNameTrim = baseName.trim()
                val specializationTrim = specialization.trim()

                skill.copy(
                    name = "$baseNameTrim  ($specializationTrim)",
                    isBasic = skill.isBasic,
                    specialization = specializationTrim
                )
            } else {
                skill
            }
        }
    }
}
