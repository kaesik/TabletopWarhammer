package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import database.SpeciesEntity
import kotlinx.serialization.json.Json

fun SpeciesEntity.toSpeciesItem(): SpeciesItem {
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

    return SpeciesItem(
        id = id,
        name = name,
        description = description,
        opinions = opinions,
        source = source,
        weaponSkill = weaponSkill,
        ballisticSkill = ballisticSkill,
        strength = strength,
        toughness = toughness,
        agility = agility,
        dexterity = dexterity,
        intelligence = intelligence,
        willpower = willpower,
        fellowship = fellowship,
        wounds = wounds,
        fatePoints = fatePoints,
        resilience = resilience,
        extraPoints = extraPoints,
        movement = movement,
        skills = skills,
        talents = talents,
        forenames = forenames,
        surnames = surnames,
        clans = clans,
        epithets = epithets,
        age = age,
        eyeColour = eyeColour,
        hairColour = hairColour,
        height = height,
        initiative = initiative,
        page = page?.toInt(),
        names = names
    )
}

fun SpeciesDto.toSpeciesItem(): SpeciesItem {
    return SpeciesItem(
        id = id,
        name = name,
        description = description,
        opinions = opinions,
        source = source,
        weaponSkill = weaponSkill,
        ballisticSkill = ballisticSkill,
        strength = strength,
        toughness = toughness,
        agility = agility,
        dexterity = dexterity,
        intelligence = intelligence,
        willpower = willpower,
        fellowship = fellowship,
        wounds = wounds,
        fatePoints = fatePoints,
        resilience = resilience,
        extraPoints = extraPoints,
        movement = movement,
        skills = skills,
        talents = talents,
        forenames = forenames,
        surnames = surnames,
        clans = clans,
        epithets = epithets,
        age = age,
        eyeColour = eyeColour,
        hairColour = hairColour,
        height = height,
        initiative = initiative,
        page = page,
        names = names
    )
}
