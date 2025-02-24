package com.kaesik.tabletopwarhammer.library.domain.library.items

data class TalentItem(
    val id: String,
    val name: String,
    val max: String,
    val tests: String,
    val description: String,
    val source: String,
    val page: Double
)
