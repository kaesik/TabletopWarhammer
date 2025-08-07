package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class CareerPathItem(
    override val id: String,
    override val name: String,
    val status: String? = null,
    val skills: String? = null,
    val trappings: String? = null,
    val talents: String? = null,
) : LibraryItem {
    companion object {
        fun default(): CareerPathItem = CareerPathItem(
            id = "",
            name = "",
            status = null,
            skills = null,
            trappings = null,
            talents = null
        )
    }
}
