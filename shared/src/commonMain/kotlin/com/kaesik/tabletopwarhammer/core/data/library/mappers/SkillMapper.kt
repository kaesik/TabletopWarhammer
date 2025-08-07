package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import database.SkillEntity
import kotlinx.serialization.json.Json

fun SkillEntity.toSkillItem(): SkillItem {
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

    return SkillItem(
        id = id,
        name = name,
        attribute = attribute,
        isBasic = isBasic?.toInt() == 1,
        isGrouped = isGrouped?.toInt() == 1,
        description = description,
        specialization = specialization,
        source = source,
        page = page?.toInt()
    )
}

fun SkillDto.toSkillItem(): SkillItem {
    return SkillItem(
        id = id,
        name = name,
        attribute = attribute,
        isBasic = isBasic,
        isGrouped = isGrouped,
        description = description,
        specialization = specialization,
        source = source,
        page = page
    )
}
