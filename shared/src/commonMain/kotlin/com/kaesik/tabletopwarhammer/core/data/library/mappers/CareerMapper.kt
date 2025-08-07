package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import database.CareerEntity
import kotlinx.serialization.json.Json

fun CareerEntity.toCareerItem(): CareerItem {
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

    return CareerItem(
        id = id,
        name = name,
        limitations = limitations,
        description = description,
        advanceScheme = advanceScheme,
        quotations = quotations,
        adventuring = adventuring,
        source = source,
        careerPath = careerPath,
        className = className,
        page = page?.toInt()
    )
}

fun CareerDto.toCareerItem(): CareerItem {
    return CareerItem(
        id = id,
        name = name,
        limitations = limitations,
        description = description,
        advanceScheme = advanceScheme,
        quotations = quotations,
        adventuring = adventuring,
        source = source,
        careerPath = careerPath,
        className = className,
        page = page
    )
}
