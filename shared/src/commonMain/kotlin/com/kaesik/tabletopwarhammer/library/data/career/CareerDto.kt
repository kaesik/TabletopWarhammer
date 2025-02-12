package com.kaesik.tabletopwarhammer.library.data.career

import kotlinx.serialization.Serializable

@Serializable
data class CareerDto(
    val id: String,
    val name: String,
    val description: String,
    val shortName: String,
    val page: Int,
)
