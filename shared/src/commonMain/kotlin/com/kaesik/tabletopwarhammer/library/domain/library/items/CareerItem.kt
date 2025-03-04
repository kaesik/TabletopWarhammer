package com.kaesik.tabletopwarhammer.library.domain.library.items

data class CareerItem (
    override val id: String,
    override val name: String,
    val limitations: String,
    val description: String,
    val advanceScheme: String,
    val quotations: String,
    val adventuring: String,
    val source: String,
    val careerPath: String,
    val className: String,
    val page: Int,
): LibraryItem
