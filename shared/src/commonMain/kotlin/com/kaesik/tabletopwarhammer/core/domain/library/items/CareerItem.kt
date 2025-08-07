package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class CareerItem(
    override val id: String,
    override val name: String,
    val limitations: String? = null,
    val description: String? = null,
    val advanceScheme: String? = null,
    val quotations: String? = null,
    val adventuring: String? = null,
    val source: String? = null,
    val careerPath: String? = null,
    val className: String? = null,
    val page: Int? = null,
) : LibraryItem {
    companion object {
        fun default(): CareerItem = CareerItem(
            id = "",
            name = "",
            limitations = null,
            description = null,
            advanceScheme = null,
            quotations = null,
            adventuring = null,
            source = null,
            careerPath = null,
            className = null,
            page = null
        )
    }
}
