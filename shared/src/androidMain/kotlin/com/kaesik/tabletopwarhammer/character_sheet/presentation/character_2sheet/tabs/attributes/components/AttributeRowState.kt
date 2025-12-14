package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.attributes.components

data class AttributeRowState(
    val shortLabel: String,
    val fullLabel: String,
    val base: Int,
    val advances: Int,
    val current: Int,
    val onAdvancesChange: (Int) -> Unit
)
