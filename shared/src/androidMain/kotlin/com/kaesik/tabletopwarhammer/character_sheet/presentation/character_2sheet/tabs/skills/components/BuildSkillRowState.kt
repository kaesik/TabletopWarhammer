package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.components

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue

fun buildSkillRowState(
    character: CharacterItem,
    name: String,
    entries: List<List<String>>,
    isBasic: Boolean,
    onCharacterChange: (CharacterItem) -> Unit
): SkillRowState {
    val advances = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
    val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
    val base = getAttributeValue(character, attr)
    val total = base + advances

    val onAdvChange: (Int) -> Unit = { newAdv ->
        val value = newAdv.coerceAtLeast(0)
        if (isBasic) {
            var used = false
            val updated = character.basicSkills.map { entry ->
                if (entry.getOrNull(0) != name) entry
                else {
                    val mutable = entry.toMutableList()
                    while (mutable.size < 3) mutable.add("0")
                    val thisValue = if (!used) {
                        used = true
                        value
                    } else 0
                    mutable[2] = thisValue.toString()
                    mutable
                }
            }
            onCharacterChange(character.copy(basicSkills = updated))
        } else {
            var used = false
            val updated = character.advancedSkills.map { entry ->
                if (entry.getOrNull(0) != name) entry
                else {
                    val mutable = entry.toMutableList()
                    while (mutable.size < 3) mutable.add("0")
                    val thisValue = if (!used) {
                        used = true
                        value
                    } else 0
                    mutable[2] = thisValue.toString()
                    mutable
                }
            }
            onCharacterChange(character.copy(advancedSkills = updated))
        }
    }

    return SkillRowState(
        name = name,
        characteristicName = attr,
        characteristicValue = base,
        advances = advances,
        skill = total,
        onAdvancesChange = onAdvChange
    )
}
