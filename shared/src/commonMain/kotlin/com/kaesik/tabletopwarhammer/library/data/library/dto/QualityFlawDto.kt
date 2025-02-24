package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QualityFlawDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: String,
    @SerialName("description")
    val description: String,
    @SerialName("is_quality")
    val isQuality: Boolean,
    @SerialName("source")
    val source: String,
    @SerialName("page")
    val page: Double
)
