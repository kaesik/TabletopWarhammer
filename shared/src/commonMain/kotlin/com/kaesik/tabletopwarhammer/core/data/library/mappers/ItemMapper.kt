package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.ItemItem
import database.ItemEntity
import kotlinx.serialization.json.Json

fun ItemEntity.toItemItem(): ItemItem {
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

    return ItemItem(
        id = id,
        name = name,
        group = group,
        source = source,
        ap = ap,
        availability = availability,
        carries = carries,
        damage = damage,
        description = description,
        encumbrance = encumbrance,
        isTwoHanded = isTwoHanded?.toInt() == 1,
        locations = locations,
        penalty = penalty,
        price = price,
        qualitiesAndFlaws = qualitiesAndFlaws,
        quantity = quantity,
        range = range,
        meeleRanged = meeleRanged,
        type = type,
        page = page?.toInt(),
        updatedAt = updatedAt.toString()
    )
}

fun ItemDto.toItemItem(): ItemItem {
    return ItemItem(
        id = id,
        name = name,
        group = group,
        source = source,
        ap = ap,
        availability = availability,
        carries = carries,
        damage = damage,
        description = description,
        encumbrance = encumbrance,
        isTwoHanded = isTwoHanded,
        locations = locations,
        penalty = penalty,
        price = price,
        qualitiesAndFlaws = qualitiesAndFlaws,
        quantity = quantity,
        range = range,
        meeleRanged = meeleRanged,
        type = type,
        page = page,
        updatedAt = updatedAt
    )
}
