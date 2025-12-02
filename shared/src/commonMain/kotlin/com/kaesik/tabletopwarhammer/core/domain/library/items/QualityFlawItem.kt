package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class QualityFlawItem(
    override val id: String,
    override val name: String,
    val group: String? = null,
    val description: String? = null,
    val isQuality: Boolean? = null,
    val source: String? = null,
    val page: Int? = null,
    override val updatedAt: String? = null
) : LibraryItem {
    companion object {
        fun default(): QualityFlawItem = QualityFlawItem(
            id = "",
            name = "",
            group = null,
            description = null,
            isQuality = null,
            source = null,
            page = null
        )
    }
}
