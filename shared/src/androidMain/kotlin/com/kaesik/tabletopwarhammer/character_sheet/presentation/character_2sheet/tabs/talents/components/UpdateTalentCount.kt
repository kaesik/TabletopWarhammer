package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents.components

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

fun updateTalentCount(
    character: CharacterItem,
    name: String,
    newCount: Int
): List<List<String>> {
    val baseList = character.talents
    val count = newCount.coerceAtLeast(0)
    val template = baseList.firstOrNull { it.getOrNull(0) == name } ?: listOf(name)

    val result = mutableListOf<List<String>>()
    var inserted = false

    for (entry in baseList) {
        if (entry.getOrNull(0) == name) {
            if (!inserted) {
                repeat(count) {
                    result.add(template)
                }
                inserted = true
            }
        } else {
            result.add(entry)
        }
    }

    if (!inserted && count > 0) {
        repeat(count) {
            result.add(template)
        }
    }

    return result
}
