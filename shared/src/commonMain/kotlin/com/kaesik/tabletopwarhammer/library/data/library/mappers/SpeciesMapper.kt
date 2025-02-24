package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.SpeciesDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.SpeciesItem

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
