package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import database.ClassEntity
import kotlinx.serialization.json.Json

fun ClassEntity.toClassItem(): ClassItem {
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

    return ClassItem(
        id = id,
        name = name,
        description = description,
        trappings = trappings,
        careers = careers,
        page = page?.toInt()
    )
}

fun ClassDto.toClassItem(): ClassItem {
    return ClassItem(
        id = id,
        name = name,
        description = description,
        trappings = trappings,
        careers = careers,
        page = page
    )
}
