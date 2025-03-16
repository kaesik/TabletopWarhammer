package com.kaesik.tabletopwarhammer.library.domain.library.items

data class AttributeItem(
    override val id: String,
    override val name: String,
    val description: String?,
    val shortName: String?,
    val page: Int?,
): LibraryItem
