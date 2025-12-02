package com.kaesik.tabletopwarhammer.core.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CareerDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("limitations")
    val limitations: String?,
    @SerialName("summary")
    val summary: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("advance_scheme")
    val advanceScheme: String?,
    @SerialName("quotations")
    val quotations: String?,
    @SerialName("adventuring")
    val adventuring: String?,
    @SerialName("source")
    val source: String?,
    @SerialName("career_path")
    val careerPath: String?,
    @SerialName("class_name")
    val className: String?,
    @SerialName("page")
    val page: Int?,
    @SerialName("updated_at")
    val updatedAt: String?,
)
