package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

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
