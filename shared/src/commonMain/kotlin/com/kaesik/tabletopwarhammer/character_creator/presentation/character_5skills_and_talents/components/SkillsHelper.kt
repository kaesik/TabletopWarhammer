package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem

fun canon(name: String): String {
    val trimmed = name.trim()
    val i = trimmed.indexOf('(')
    return if (i >= 0) {
        val base = trimmed.substring(0, i).trim()
        val rest = trimmed.substring(i).trim()
        "$base $rest"
    } else trimmed
}

fun baseOf(name: String): String = canon(name).substringBefore("(").trim()

fun inSameOrGroup(a: String, b: String, groups: Map<String, Set<String>>): Boolean {
    val aC = canon(a)
    val bC = canon(b)
    val baseA = baseOf(aC)
    val baseB = baseOf(bC)
    if (baseA != baseB) return false
    val set = groups[baseA] ?: return false
    return aC in set && bC in set
}

fun mapSpecializedSkills(skillLists: List<List<SkillItem>>): List<List<SkillItem>> {
    val parenRegex = Regex("""^(.*)\s*\((.*)\)$""")
    val orSplit = Regex("\\s+or\\s+", RegexOption.IGNORE_CASE)

    return skillLists.map { list ->
        list.flatMap { skill ->
            val name = canon(skill.name)
            val match = parenRegex.matchEntire(name)
            if (match != null) {
                val (baseNameRaw, insideRaw) = match.destructured
                val baseName = baseNameRaw.trim()
                val inside = insideRaw.trim()

                val options = inside.split(orSplit)
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }

                val specializations = if (options.size >= 2) options else listOf(inside)

                specializations.map { spec ->
                    skill.copy(
                        name = canon("$baseName ($spec)"),
                        specialization = spec
                    )
                }
            } else {
                listOf(skill.copy(name = name))
            }
        }
    }
}
