package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class TalentItem(
    override val id: String,
    override val name: String,
    val max: String? = null,
    val tests: String? = null,
    val description: String? = null,
    val source: String? = null,
    val page: Int? = null,
) : LibraryItem
