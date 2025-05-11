package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class SkillItem(
    override val id: String,
    override val name: String,
    val attribute: String? = null,
    val isBasic: Boolean? = null,
    val isGrouped: Boolean? = null,
    val description: String? = null,
    val specialization: String? = null,
    val source: String? = null,
    val page: Int? = null,
) : LibraryItem
