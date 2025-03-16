package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SkillDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("attribute")
    val attribute: String?,
    @SerialName("is_basic")
    val isBasic: Boolean?,
    @SerialName("is_grouped")
    val isGrouped: Boolean?,
    @SerialName("description")
    val description: String?,
    @SerialName("specialization")
    val specialization: String?,
    @SerialName("source")
    val source: String?,
    @SerialName("page")
    val page: Int?,
)
