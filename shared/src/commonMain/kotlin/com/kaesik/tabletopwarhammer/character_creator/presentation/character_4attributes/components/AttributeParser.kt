package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components

data class AttributeRoll(
    val diceThrow: String,
    val baseValue: Int,
)

fun parseAttributeFormula(formula: String?): AttributeRoll {
    if (formula.isNullOrBlank()) return AttributeRoll(diceThrow = "0d0", baseValue = 0)

    val parts = formula.split("+")
    val dice = parts.getOrNull(0)?.trim() ?: "0d0"
    val base = parts.getOrNull(1)?.trim()?.toIntOrNull() ?: 0

    return AttributeRoll(
        diceThrow = dice,
        baseValue = base
    )
}
