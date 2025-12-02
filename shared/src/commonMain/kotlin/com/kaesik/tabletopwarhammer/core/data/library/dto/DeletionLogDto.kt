package com.kaesik.tabletopwarhammer.core.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeletionLogDto(
    @SerialName("entity_id")
    val entityId: String,
    @SerialName("deleted_at")
    val deletedAt: String
)
