package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class AttributeItem(
    override val id: String,
    override val name: String,
    val description: String? = null,
    val shortName: String? = null,
    val page: Int? = null,
    override val updatedAt: String? = null,
) : LibraryItem {
    companion object {
        fun default(): AttributeItem {
            return AttributeItem(
                id = "",
                name = "",
                description = null,
                shortName = null,
                page = null
            )
        }
    }
}
