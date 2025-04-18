package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class CareerPathItem(
    override val id: String,
    override val name: String,
    val status: String?,
    val skills: String?,
    val trappings: String?,
    val talents: String?,
) : LibraryItem
