package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem

data class TalentItemGroup(
    val options: List<TalentItem>,
    val mustChooseOne: Boolean = true
)

fun extractTalents(raw: String?): List<List<String>> {
    return raw
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?.map { part ->
            if (" or " in part.lowercase()) {
                part.split(" or ").map { it.trim() }
            } else listOf(part)
        } ?: emptyList()
}
