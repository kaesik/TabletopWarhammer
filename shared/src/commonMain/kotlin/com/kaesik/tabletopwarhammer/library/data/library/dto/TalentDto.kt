package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TalentDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("max")
    val max: String,
    @SerialName("tests")
    val tests: String,
    @SerialName("description")
    val description: String,
    @SerialName("source")
    val source: String,
    @SerialName("page")
    val page: Int
)
