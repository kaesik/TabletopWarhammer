package com.kaesik.tabletopwarhammer.core.data.character

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import database.CharacterEntity
import kotlinx.serialization.json.Json

fun CharacterEntity.toCharacterItem(): CharacterItem {
    val jsonParser = Json { ignoreUnknownKeys = true }

    fun parseIntList(json: String): List<Int> =
        jsonParser.decodeFromString(json)

    fun parseStringList(json: String): List<String> =
        jsonParser.decodeFromString(json)

    fun parseListOfStringLists(json: String): List<List<String>> =
        jsonParser.decodeFromString(json)

    return CharacterItem(
        id = id.toInt(),
        name = name,
        species = species,
        cLass = class_,
        career = career,
        careerLevel = careerLevel,
        careerPath = careerPath,
        status = status,
        age = age,
        height = height,
        hair = hair,
        eyes = eyes,
        weaponSkill = parseIntList(weaponSkill),
        ballisticSkill = parseIntList(ballisticSkill),
        strength = parseIntList(strength),
        toughness = parseIntList(toughness),
        initiative = parseIntList(initiative),
        agility = parseIntList(agility),
        dexterity = parseIntList(dexterity),
        intelligence = parseIntList(intelligence),
        willPower = parseIntList(willPower),
        fellowship = parseIntList(fellowship),
        fate = fate.toInt(),
        fortune = fortune.toInt(),
        resilience = resilience.toInt(),
        resolve = resolve.toInt(),
        motivation = motivation,
        experience = parseIntList(experience),
        movement = movement.toInt(),
        walk = walk.toInt(),
        run = run.toInt(),
        basicSkills = parseListOfStringLists(basicSkills),
        advancedSkills = parseListOfStringLists(advancedSkills),
        talents = parseListOfStringLists(talents),
        ambitionShortTerm = ambitionShortTerm,
        ambitionLongTerm = ambitionLongTerm,
        partyName = partyName,
        partyAmbitionShortTerm = partyAmbitionShortTerm,
        partyAmbitionLongTerm = partyAmbitionLongTerm,
        partyMembers = parseStringList(partyMembers),
        armour = parseListOfStringLists(armour),
        weapons = parseListOfStringLists(weapons),
        trappings = parseStringList(trappings),
        psychology = parseStringList(psychology),
        mutations = parseStringList(mutations),
        wealth = parseIntList(wealth),
        encumbrance = parseIntList(encumbrance),
        wounds = parseIntList(wounds),
        spells = parseListOfStringLists(spells),
        prayers = parseListOfStringLists(prayers),
        sin = sin.toInt()
    )
}
