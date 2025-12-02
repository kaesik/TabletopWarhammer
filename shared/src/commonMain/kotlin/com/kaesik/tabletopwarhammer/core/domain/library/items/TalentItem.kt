package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class TalentItem(
    override val id: String,
    override val name: String,
    val max: String? = null,
    val tests: String? = null,
    val description: String? = null,
    val specialization: String? = null,
    val source: String? = null,
    val page: Int? = null,
    override val updatedAt: String? = null
) : LibraryItem {
    companion object {
        fun default(): TalentItem = TalentItem(
            id = "",
            name = "",
            max = null,
            tests = null,
            description = null,
            specialization = null,
            source = null,
            page = null
        )
    }
}
