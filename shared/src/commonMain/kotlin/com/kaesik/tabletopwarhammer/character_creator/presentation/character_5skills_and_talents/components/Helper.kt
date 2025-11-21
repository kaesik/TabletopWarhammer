package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

fun normalizeSkillOrTalentName(name: String): String {
    val trimmed = name.trim()
    val i = trimmed.indexOf('(')
    return if (i >= 0) {
        val base = trimmed.substring(0, i).trim()
        val rest = trimmed.substring(i).trim()
        "$base $rest"
    } else trimmed
}

fun getBaseName(name: String): String = normalizeSkillOrTalentName(name).substringBefore("(").trim()

fun getFullNameWithSpecialization(base: String, spec: String): String =
    "${base.trim()} (${spec.trim()})"

fun hasAnyMarker(name: String): Boolean {
    return Regex("\\bany\\b", RegexOption.IGNORE_CASE).containsMatchIn(name)
}

fun inSameOrGroup(a: String, b: String, groups: Map<String, Set<String>>): Boolean {
    val aC = normalizeSkillOrTalentName(a)
    val bC = normalizeSkillOrTalentName(b)
    val baseA = getBaseName(aC)
    val baseB = getBaseName(bC)
    if (baseA != baseB) return false
    val set = groups[baseA] ?: return false
    return aC in set && bC in set
}

fun mapSpecializedSkills(skillLists: List<List<SkillItem>>): List<List<SkillItem>> {
    val parenRegex = Regex("""^(.*)\s*\((.*)\)$""")
    val orSplit = Regex("\\s+or\\s+", RegexOption.IGNORE_CASE)

    return skillLists.map { list ->
        list.flatMap { skill ->
            val name = normalizeSkillOrTalentName(skill.name)
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
                        name = normalizeSkillOrTalentName("$baseName ($spec)"),
                        specialization = spec
                    )
                }
            } else {
                listOf(skill.copy(name = name))
            }
        }
    }
}

fun extractTalents(raw: String?): List<List<String>> {
    return raw
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?.flatMap { entry ->
            when {
                " or " in entry -> {
                    listOf(entry.split(" or ").map { it.trim() })
                }

                entry.contains("Random", ignoreCase = true) -> {
                    val count = entry.split(" ").firstOrNull()?.toIntOrNull() ?: 1
                    List(count) { listOf("Random Talent") }
                }

                else -> listOf(listOf(entry))
            }
        } ?: emptyList()
}

fun normalizeTalentGroups(rawGroups: List<List<TalentItem>>): List<List<TalentItem>> {
    val orRegex = Regex("\\s+or\\s+", RegexOption.IGNORE_CASE)
    val randCountRegex = Regex("(?i)^(\\d+)\\s+Random\\s+Talents?$")
    val randRegex = Regex("(?i)^Random\\s+Talents?$")

    return rawGroups.flatMap { group ->
        // Extract names, splitting by commas if there's only one item in the group
        val names: List<String> = if (group.size == 1) {
            group[0].name.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        } else {
            group.map { it.name.trim() }
        }

        names.flatMap { entry ->
            when {
                // "Talent A or Talent B"
                orRegex.containsMatchIn(entry) -> {
                    val base = group[0]
                    val options = orRegex.split(entry).map { it.trim() }.filter { it.isNotEmpty() }
                    println("OR split: '$entry' -> $options")
                    listOf(options.map { opt -> base.copy(name = opt) })
                }

                // "n Random Talent"
                randCountRegex.matches(entry) -> {
                    val n = randCountRegex.find(entry)!!.groupValues[1].toInt().coerceAtLeast(1)
                    val base = group[0]
                    println("RANDOM count: '$entry' -> $n")
                    listOf(List(n) { base.copy(name = "Random Talent") })
                }

                // "Random Talent"
                randRegex.matches(entry) -> {
                    val base = group[0]
                    listOf(listOf(base.copy(name = "Random Talent")))
                }

                // "Talent A"
                else -> {
                    val base = group[0]
                    listOf(listOf(base.copy(name = entry)))
                }
            }
        }

    }
}

fun extractSpec(fullName: String): String? =
    fullName.substringAfter("(", "").removeSuffix(")").trim().ifBlank { null }

fun groupSkills(skills: List<SkillItem>): Map<String, List<SkillItem>> =
    skills.map { it.copy(name = normalizeSkillOrTalentName(it.name)) }
        .groupBy { getBaseName(it.name) }
        .mapValues { (_, v) -> v.sortedBy(SkillItem::name) }

fun nonAnyVariantNames(group: List<SkillItem>) =
    group.filterNot { hasAnyMarker(it.name) }.map { normalizeSkillOrTalentName(it.name) }.toSet()

fun nonAnyVariantSpecsLower(group: List<SkillItem>) =
    group.filterNot { hasAnyMarker(it.name) }.mapNotNull { extractSpec(it.name)?.lowercase() }
        .toSet()
