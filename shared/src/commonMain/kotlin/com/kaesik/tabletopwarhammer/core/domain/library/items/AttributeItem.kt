package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class AttributeItem(
    override val id: String,
    override val name: String,
    val description: String?,
    val shortName: String?,
    val page: Int?,
) : LibraryItem
