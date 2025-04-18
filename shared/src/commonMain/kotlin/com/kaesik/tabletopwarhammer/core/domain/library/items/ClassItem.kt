package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class ClassItem(
    override val id: String,
    override val name: String,
    val description: String?,
    val trappings: String?,
    val careers: String?,
    val page: Int?,
) : LibraryItem
