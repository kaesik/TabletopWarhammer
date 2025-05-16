package com.kaesik.tabletopwarhammer.character_creator.domain

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.util.Resource

class CharacterCreator(
    private val client: CharacterCreatorClient,
    private val characterDataSource: CharacterDataSource,
) {
    suspend fun createCharacter(): Resource<String> {
        return try {
            characterDataSource.insertCharacter(
                CharacterItem(
                    id = TODO(),
                    name = TODO(),
                    species = TODO(),
                    cLass = TODO(),
                    career = TODO(),
                    careerLevel = TODO(),
                    careerPath = TODO(),
                    status = TODO(),
                    age = TODO(),
                    height = TODO(),
                    hair = TODO(),
                    eyes = TODO(),
                    weaponSkill = TODO(),
                    ballisticSkill = TODO(),
                    strength = TODO(),
                    toughness = TODO(),
                    initiative = TODO(),
                    agility = TODO(),
                    dexterity = TODO(),
                    intelligence = TODO(),
                    willPower = TODO(),
                    fellowship = TODO(),
                    fate = TODO(),
                    fortune = TODO(),
                    resilience = TODO(),
                    resolve = TODO(),
                    motivation = TODO(),
                    experience = TODO(),
                    movement = TODO(),
                    walk = TODO(),
                    run = TODO(),
                    basicSkills = TODO(),
                    advancedSkills = TODO(),
                    talents = TODO(),
                    ambitionShortTerm = TODO(),
                    ambitionLongTerm = TODO(),
                    partyName = TODO(),
                    partyAmbitionShortTerm = TODO(),
                    partyAmbitionLongTerm = TODO(),
                    partyMembers = TODO(),
                    armour = TODO(),
                    weapons = TODO(),
                    trappings = TODO(),
                    psychology = TODO(),
                    mutations = TODO(),
                    wealth = TODO(),
                    encumbrance = TODO(),
                    wounds = TODO(),
                    spells = TODO(),
                    prayers = TODO(),
                    sin = TODO()
                )
            )
            Resource.Success("Character created successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e)
        }
    }
}
