package com.kaesik.tabletopwarhammer.core.domain.character

data class CharacterItem(
    val id: Int,

    // CHARACTER
    val name: String,
    val species: String,
    val cLass: String,
    val career: String,
    val careerLevel: String,
    val careerPath: String,
    val status: String,
    val age: String,
    val height: String,
    val hair: String,
    val eyes: String,

    // CHARACTERISTICS
    // CHARACTERISTICS[INITIAL,ADVANCES,CURRENT]
    val weaponSkill: List<Int>,
    val ballisticSkill: List<Int>,
    val strength: List<Int>,
    val toughness: List<Int>,
    val initiative: List<Int>,
    val agility: List<Int>,
    val dexterity: List<Int>,
    val intelligence: List<Int>,
    val willPower: List<Int>,
    val fellowship: List<Int>,

    // FATE
    val fate: Int,
    val fortune: Int,

    // RESILIENCE
    val resilience: Int,
    val resolve: Int,
    val motivation: String,

    // EXPERIENCE
    // EXPERIENCE[CURRENT,SPENT,TOTAL]
    val experience: List<Int>,

    // MOVEMENT
    val movement: Int,
    val walk: Int,
    val run: Int,

    // SKILLS
    // BASIC_SKILLS[NAME[CHARACTERISTICS,ADVANCED,SKILL]
    val basicSkills: List<List<String>>,
    // ADVANCED_SKILLS[NAME[CHARACTERISTICS,ADVANCED,SKILL]
    val advancedSkills: List<List<String>>,

    // TALENTS
    // TALENTS[NAME[CHARACTERISTICS,ADVANCED,SKILL]
    val talents: List<List<String>>,

    // AMBITIONS
    val ambitionShortTerm: String,
    val ambitionLongTerm: String,

    // PARTY
    val partyName: String,
    val partyAmbitionShortTerm: String,
    val partyAmbitionLongTerm: String,
    val partyMembers: List<String>,

    // ARMOUR
    // ARMOUR[NAME[LOCATIONS,ENC,AP,QUALITIES]
    val armour: List<List<String>>,

    // WEAPONS
    // WEAPONS[NAME[GROUP,ENC,RANGE,DAMAGE,QUALITIES]
    val weapons: List<List<String>>,

    // TRAPPINGS
    // TRAPPINGS[NAME]
    val trappings: List<String>,

    // PSYCHOLOGY & MUTATIONS
    val psychology: List<String>,
    val mutations: List<String>,
    val corruption: Int,

    // WEALTH
    val wealth: List<Int>,

    // ENCUMBRANCE
    val encumbrance: List<Int>,

    // WOUNDS[ACTUAL,MAX]
    val wounds: List<Int>,
    val woundsFormula: String,

    // SPELLS & PRAYERS
    // SPELLS[NAME[CN,RANGE,TARGET,DURATION,EFFECT]
    val spells: List<List<String>>,
    // PRAYERS[NAME[CN,RANGE,TARGET,DURATION,EFFECT]
    val prayers: List<List<String>>,
    val sin: Int,

    // METADATA
    val updatedAt: String?,
    val createdAt: String?,
    val deletedAt: String?,
) {
    companion object {
        fun default() = CharacterItem(
            id = 0,
            name = "xxx",
            species = "xxx",
            cLass = "xxx",
            career = "xxx",
            careerLevel = "xxx",
            careerPath = "xxx",
            status = "xxx",
            age = "xxx",
            height = "xxx",
            hair = "xxx",
            eyes = "xxx",
            weaponSkill = listOf(0, 0, 0),
            ballisticSkill = listOf(0, 0, 0),
            strength = listOf(0, 0, 0),
            toughness = listOf(0, 0, 0),
            initiative = listOf(0, 0, 0),
            agility = listOf(0, 0, 0),
            dexterity = listOf(0, 0, 0),
            intelligence = listOf(0, 0, 0),
            willPower = listOf(0, 0, 0),
            fellowship = listOf(0, 0, 0),
            fate = 0,
            fortune = 0,
            resilience = 0,
            resolve = 0,
            motivation = "xxx",
            experience = listOf(0, 0, 0),
            movement = 0,
            walk = 0,
            run = 0,
            basicSkills = emptyList(),
            advancedSkills = emptyList(),
            talents = emptyList(),
            ambitionShortTerm = "xxx",
            ambitionLongTerm = "xxx",
            partyName = "xxx",
            partyAmbitionShortTerm = "xxx",
            partyAmbitionLongTerm = "xxx",
            partyMembers = emptyList(),
            armour = emptyList(),
            weapons = emptyList(),
            trappings = emptyList(),
            psychology = emptyList(),
            mutations = emptyList(),
            corruption = 0,
            wealth = listOf(0, 0, 0),
            encumbrance = listOf(0, 0, 0),
            wounds = listOf(0, 0),
            woundsFormula = "xxx",
            spells = emptyList(),
            prayers = emptyList(),
            sin = 0,
            updatedAt = null,
            createdAt = null,
            deletedAt = null,
        )
    }
}
