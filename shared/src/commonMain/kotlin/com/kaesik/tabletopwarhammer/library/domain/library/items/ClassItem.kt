package com.kaesik.tabletopwarhammer.library.domain.library.items

data class ClassItem(
    val id: String,
    val name: String,
    val description: String,
    val trappings: String,
    val careers: String,
    val page: Double
)
