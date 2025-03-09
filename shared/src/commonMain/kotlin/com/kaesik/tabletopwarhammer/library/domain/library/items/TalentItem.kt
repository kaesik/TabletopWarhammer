package com.kaesik.tabletopwarhammer.library.domain.library.items

data class TalentItem(
    override val id: String,
    override val name: String,
    val max: String,
    val tests: String,
    val description: String,
    val source: String,
    val page: Int
): LibraryItem
