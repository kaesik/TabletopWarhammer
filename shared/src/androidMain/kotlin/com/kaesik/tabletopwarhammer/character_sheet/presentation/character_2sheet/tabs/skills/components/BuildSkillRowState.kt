package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.components

import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetEvent
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.character.getAttributeValue

fun buildSkillRowState(
    character: CharacterItem,
    name: String,
    entries: List<List<String>>,
    isBasic: Boolean,
    onEvent: (CharacterSheetEvent) -> Unit
): SkillRowState {
    val advances = entries.sumOf { it.getOrNull(2)?.toIntOrNull() ?: 0 }
    val attr = entries.firstOrNull()?.getOrNull(1).orEmpty()
    val base = getAttributeValue(character, attr)
    val total = base + advances

    val onAdvChange: (Int) -> Unit = { newAdv ->
        onEvent(
            CharacterSheetEvent.SetSkillAdvances(
                name = name,
                isBasic = isBasic,
                advances = newAdv
            )
        )
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
