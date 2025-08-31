package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

sealed class CharacterCreatorEvent {
    data object OnCreateCharacterSelect : CharacterCreatorEvent()
    data object OnRandomCharacterSelect : CharacterCreatorEvent()

    data class ShowMessage(val message: String) : CharacterCreatorEvent()
    data object ClearMessage : CharacterCreatorEvent()

    data class AddExperience(val experience: Int) : CharacterCreatorEvent()
    data class RemoveExperience(val experience: Int) : CharacterCreatorEvent()

    data class SetHasRolledSpecies(val value: Boolean) : CharacterCreatorEvent()
    data class SetHasChosenSpecies(val value: Boolean) : CharacterCreatorEvent()
    data class SetSpecies(val speciesItem: SpeciesItem) : CharacterCreatorEvent()

    data class SetHasRolledClassAndCareer(val value: Boolean) : CharacterCreatorEvent()
    data class SetHasChosenClassAndCareer(val value: Boolean) : CharacterCreatorEvent()
    data class SetClass(val classItem: ClassItem) : CharacterCreatorEvent()
    data class SetCareer(
        val careerItem: CareerItem?,
        val careerPathItem: CareerPathItem?
    ) : CharacterCreatorEvent()

    data class SaveRolledAttributes(
        val rolled: List<Int>,
        val total: List<Int>
    ) : CharacterCreatorEvent()

    data class SetAttributes(
        val rolledAttributes: List<Int>? = null,
        val totalAttributes: List<Int>? = null,
        val fatePoints: Int? = null,
        val resiliencePoints: Int? = null
    ) : CharacterCreatorEvent()

    data class SetHasRolledAttributes(val value: Boolean) : CharacterCreatorEvent()
    data class SetHasReorderAttributes(val value: Boolean) : CharacterCreatorEvent()
    data class SetHasAllocateAttributes(val value: Boolean) : CharacterCreatorEvent()
    data class SetRollLocked(val locked: Boolean) : CharacterCreatorEvent()
    data class SetRolledFromAllocation(val value: Boolean) : CharacterCreatorEvent()

    data class SetSkillsAndTalents(
        val speciesBasicSkills: List<List<String>>,
        val speciesAdvancedSkills: List<List<String>>,
        val careerBasicSkills: List<List<String>> = emptyList(),
        val careerAdvancedSkills: List<List<String>> = emptyList(),
        val talents: List<List<String>>
    ) : CharacterCreatorEvent()

    data class SetSkillsTalentsSelections(
        val selectedSkillNames3: List<String>,
        val selectedSkillNames5: List<String>,
        val careerSkillPoints: Map<String, Int>,
        val selectedSpeciesTalentNames: List<String>,
        val selectedCareerTalentNames: List<String>,
        val rolledTalents: Map<Pair<Int, Int>, String>
    ) : CharacterCreatorEvent()

    data class SetTrappings(
        val trappings: List<String>,
    ) : CharacterCreatorEvent()

    data class SetWealth(
        val wealth: List<Int>,
    ) : CharacterCreatorEvent()

    data class SetCharacterDetails(
        val name: String,
        val age: String,
        val height: String,
        val hairColor: String,
        val eyeColor: String,
    ) : CharacterCreatorEvent()
}
