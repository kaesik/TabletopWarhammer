package com.kaesik.tabletopwarhammer.library.domain.library.career

data class CareerItem (
    val id: String,
    val name: String,
    val limitations: String,
    val description: String,
    val advanceScheme: String,
    val quotations: String,
    val adventuring: String,
    val source: String,
    val careerPath: String,
    val className: String,
    val page: Int,
)
