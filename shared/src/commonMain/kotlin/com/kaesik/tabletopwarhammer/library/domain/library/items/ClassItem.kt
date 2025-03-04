package com.kaesik.tabletopwarhammer.library.domain.library.items

data class ClassItem(
    override val id: String,
    override val name: String,
    val description: String,
    val trappings: String,
    val careers: String,
    val page: Double
): LibraryItem
