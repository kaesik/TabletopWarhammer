package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem

fun updateCurrentWoundsInternal(
    character: CharacterItem,
    delta: Int
): List<Int> {
    val list = character.wounds.toMutableList()
    while (list.size < 2) list.add(0)

    val maxWounds = list[1].coerceAtLeast(0)
    if (maxWounds <= 0) return list

    val storedCurrent = list[0]
    val effectiveCurrent = if (storedCurrent <= 0) maxWounds else storedCurrent
    val newCurrent = (effectiveCurrent + delta).coerceIn(0, maxWounds)

    list[0] = newCurrent
    return list
}
