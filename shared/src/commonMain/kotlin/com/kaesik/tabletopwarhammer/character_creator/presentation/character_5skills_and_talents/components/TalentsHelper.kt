package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

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
