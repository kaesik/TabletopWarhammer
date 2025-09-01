package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class CharacterCreatorState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val character: CharacterItem = CharacterItem.default(),

    // SPECIES
    val selectedSpecies: SpeciesItem? = null,
    val hasRolledSpecies: Boolean = false,
    val hasChosenSpecies: Boolean = false,

    // CLASS & CAREER
    val selectedClass: ClassItem? = null,
    val selectedCareer: CareerItem? = null,
    val hasRolledClassAndCareer: Boolean = false,
    val hasChosenClassAndCareer: Boolean = false,

    // ATTRIBUTES
    val rolledAttributes: List<Int> = emptyList(),
    val totalAttributes: List<Int> = emptyList(),
    val hasRolledAttributes: Boolean = false,
    val hasReorderedAttributes: Boolean = false,
    val hasAllocatedAttributes: Boolean = false,
    val rollLocked: Boolean = false,
    val rolledFromAllocation: Boolean = false,

    // SKILLS & TALENTS
    val skillsTalentsSelectedSkillNames3: List<String> = emptyList(),
    val skillsTalentsSelectedSkillNames5: List<String> = emptyList(),
    val skillsTalentsCareerSkillPoints: Map<String, Int> = emptyMap(),
    val skillsTalentsSelectedSpeciesTalentNames: List<String> = emptyList(),
    val skillsTalentsSelectedCareerTalentNames: List<String> = emptyList(),
    val skillsTalentsRolledTalents: Map<Pair<Int, Int>, String> = emptyMap(),

    // TRAPPINGS & WEALTH
    val hasGeneratedWealth: Boolean = false,
)
