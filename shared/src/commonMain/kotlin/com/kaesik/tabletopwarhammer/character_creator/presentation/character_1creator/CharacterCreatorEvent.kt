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

    data class SetSpecies(val speciesItem: SpeciesItem) : CharacterCreatorEvent()
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
        val rolledAttributes: List<Int> = emptyList(),
        val totalAttributes: List<Int> = emptyList(),
        val fatePoints: Int = 0,
        val resiliencePoints: Int = 0,
    ) : CharacterCreatorEvent()

    data class SetSkillsAndTalents(
        val speciesBasicSkills: List<List<String>>,
        val speciesAdvancedSkills: List<List<String>>,
        val careerBasicSkills: List<List<String>> = emptyList(),
        val careerAdvancedSkills: List<List<String>> = emptyList(),
        val talents: List<List<String>>
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
