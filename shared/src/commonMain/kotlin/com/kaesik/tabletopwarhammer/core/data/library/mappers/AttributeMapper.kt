package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import database.AttributeEntity
import kotlinx.serialization.json.Json

fun AttributeEntity.toAttributeItem(): AttributeItem {
    val jsonParser = Json { ignoreUnknownKeys = true }

    fun parseIntList(json: String): List<Int> =
        try {
            jsonParser.decodeFromString(json)
        } catch (_: Exception) {
            emptyList()
        }

    fun parseStringList(json: String): List<String> =
        try {
            jsonParser.decodeFromString(json)
        } catch (_: Exception) {
            emptyList()
        }

    fun parseListOfStringLists(json: String): List<List<String>> =
        try {
            jsonParser.decodeFromString(json)
        } catch (_: Exception) {
            emptyList()
        }

    return AttributeItem(
        id = id,
        name = name,
        description = description,
        shortName = shortName,
        page = page?.toInt(),
        updatedAt = updatedAt.toString(),
    )
}


fun AttributeDto.toAttributeItem(): AttributeItem {
    return AttributeItem(
        id = this.id,
        name = this.name,
        description = this.description,
        shortName = this.shortName,
        page = this.page,
        updatedAt = this.updatedAt,
    )
}
