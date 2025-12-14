package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.talents.components

data class TalentRowState(
    val name: String,
    val count: Int,
    val onCountChange: (Int) -> Unit
)
