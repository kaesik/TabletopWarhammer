package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.QualityFlawDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.QualityFlawItem
import database.QualityFlawEntity
import kotlinx.serialization.json.Json

fun QualityFlawEntity.toQualityFlawItem(): QualityFlawItem {
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

    return QualityFlawItem(
        id = id,
        name = name,
        group = group,
        description = description,
        isQuality = isQuality?.toInt() == 1,
        source = source,
        page = page?.toInt(),
        updatedAt = updatedAt.toString(),
    )
}

fun QualityFlawDto.toQualityFlawItem(): QualityFlawItem {
    return QualityFlawItem(
        id = id,
        name = name,
        group = group,
        description = description,
        isQuality = isQuality,
        source = source,
        page = page,
        updatedAt = updatedAt,
    )
}
