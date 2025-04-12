package com.kaesik.tabletopwarhammer.character_creator.domain

data class CharacterItem(
    val id: Int,

    // CHARACTER
    val name: String,
    val species: String,
    val cLass: String,
    val career: String,
    val careerLevel: String,
    val careerPath: String,
    val age: Int,
    val height: Int,
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
    // TRAPPINGS[NAME[ENC]
    val trappings: List<List<String>>,

    // PSYCHOLOGY & MUTATIONS
    val psychology: List<String>,
    val mutations: List<String>,

    // WEALTH
    val wealth: List<Int>,

    // ENCUMBRANCE
    val encumbrance: List<Int>,

    // WOUNDS
    val wounds: List<Int>,

    // SPELLS & PRAYERS
    // SPELLS[NAME[CN,RANGE,TARGET,DURATION,EFFECT]
    val spells: List<List<String>>,
    // PRAYERS[NAME[CN,RANGE,TARGET,DURATION,EFFECT]
    val prayers: List<List<String>>,
    val sin: Int
)
