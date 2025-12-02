package com.kaesik.tabletopwarhammer.core.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AttributeDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String?,
    @SerialName("short_name")
    val shortName: String?,
    @SerialName("page")
    val page: Int?,
    @SerialName("updated_at")
    val updatedAt: String?,
)
