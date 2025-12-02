package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import database.CareerPathEntity
import kotlinx.serialization.json.Json

fun CareerPathEntity.toCareerPathItem(): CareerPathItem {
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

    return CareerPathItem(
        id = id,
        name = name,
        status = status,
        skills = skills,
        trappings = trappings,
        talents = talents,
        updatedAt = updatedAt.toString(),
    )
}

fun CareerPathDto.toCareerPathItem(): CareerPathItem {
    return CareerPathItem(
        id = id,
        name = name,
        skills = skills,
        status = status,
        trappings = trappings,
        talents = talents,
        updatedAt = updatedAt,
    )
}
