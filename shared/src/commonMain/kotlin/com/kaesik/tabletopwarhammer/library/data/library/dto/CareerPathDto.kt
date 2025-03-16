package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CareerPathDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("status")
    val status: String?,
    @SerialName("skills")
    val skills: String?,
    @SerialName("trappings")
    val trappings: String?,
    @SerialName("talents")
    val talents: String?,
)
