package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import database.TalentEntity
import kotlinx.serialization.json.Json

fun TalentEntity.toTalentItem(): TalentItem {
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

    return TalentItem(
        id = id,
        name = name,
        max = max,
        tests = tests,
        description = description,
        specialization = specialization,
        source = source,
        page = page?.toInt()
    )
}

fun TalentDto.toTalentItem(): TalentItem {
    return TalentItem(
        id = id,
        name = name,
        max = max,
        tests = tests,
        description = description,
        source = source,
        page = page
    )
}
