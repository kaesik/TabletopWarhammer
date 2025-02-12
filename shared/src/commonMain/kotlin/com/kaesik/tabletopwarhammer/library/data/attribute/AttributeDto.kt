package com.kaesik.tabletopwarhammer.library.data.attribute

import kotlinx.serialization.Serializable

@Serializable
data class AttributeDto(
    val id: String,
    val name: String,
    val description: String,
    val shortName: String,
    val page: Int,
)
