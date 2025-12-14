package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.skills.components

data class SkillRowState(
    val name: String,
    val characteristicName: String,
    val characteristicValue: Int,
    val advances: Int,
    val skill: Int,
    val onAdvancesChange: (Int) -> Unit
)
