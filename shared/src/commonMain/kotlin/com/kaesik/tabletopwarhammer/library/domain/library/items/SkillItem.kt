package com.kaesik.tabletopwarhammer.library.domain.library.items

data class SkillItem(
    override val id: String,
    override val name: String,
    val attribute: String?,
    val isBasic: Boolean?,
    val isGrouped: Boolean?,
    val description: String?,
    val specialization: String?,
    val source: String?,
    val page: Int?,
): LibraryItem
