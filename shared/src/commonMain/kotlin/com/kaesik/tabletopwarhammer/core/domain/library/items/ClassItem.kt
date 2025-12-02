package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class ClassItem(
    override val id: String,
    override val name: String,
    val description: String? = null,
    val trappings: String? = null,
    val careers: String? = null,
    val page: Int? = null,
    override val updatedAt: String? = null
) : LibraryItem {
    companion object {
        fun default(): ClassItem {
            return ClassItem(
                id = "",
                name = "",
                description = null,
                trappings = null,
                careers = null,
                page = null
            )
        }
    }
}
