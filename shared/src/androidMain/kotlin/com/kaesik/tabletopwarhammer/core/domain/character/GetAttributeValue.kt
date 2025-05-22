package com.kaesik.tabletopwarhammer.core.domain.character

fun getAttributeValue(character: CharacterItem, attributeName: String): Int {
    return when (attributeName) {
        "Weapon Skill" -> character.weaponSkill.firstOrNull() ?: 0
        "Ballistic Skill" -> character.ballisticSkill.firstOrNull() ?: 0
        "Strength" -> character.strength.firstOrNull() ?: 0
        "Toughness" -> character.toughness.firstOrNull() ?: 0
        "Initiative" -> character.initiative.firstOrNull() ?: 0
        "Agility" -> character.agility.firstOrNull() ?: 0
        "Dexterity" -> character.dexterity.firstOrNull() ?: 0
        "Intelligence" -> character.intelligence.firstOrNull() ?: 0
        "Willpower" -> character.willPower.firstOrNull() ?: 0
        "Fellowship" -> character.fellowship.firstOrNull() ?: 0
        else -> 0
    }
}
