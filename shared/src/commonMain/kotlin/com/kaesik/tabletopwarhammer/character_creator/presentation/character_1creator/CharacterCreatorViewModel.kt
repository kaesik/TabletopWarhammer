package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterCreatorViewModel : ViewModel() {
    private val _state = MutableStateFlow(CharacterCreatorState())
    val state = _state.asStateFlow()

    init {
        println("CharacterCreatorViewModel initialized!")
    }

    fun onEvent(event: CharacterCreatorEvent) {
        when (event) {

            // MAIN FUNCTION
            is CharacterCreatorEvent.OnCreateCharacterSelect -> {
                _state.update { current ->
                    val updated = current.copy(
                        character = CharacterItem.default(),
                        selectedSpecies = null,
                        hasRolledSpecies = false,
                        hasChosenSpecies = false,
                        selectedClass = null,
                        selectedCareer = null,
                        hasRolledClassAndCareer = false,
                        hasChosenClassAndCareer = false,
                        rolledAttributes = emptyList(),
                        totalAttributes = emptyList(),
                        hasRolledAttributes = false,
                        hasReorderedAttributes = false,
                        hasAllocatedAttributes = false,
                        skillsTalentsSelectedSkillNames3 = emptyList(),
                        skillsTalentsSelectedSkillNames5 = emptyList(),
                        skillsTalentsCareerSkillPoints = emptyMap(),
                        skillsTalentsSelectedSpeciesTalentNames = emptyList(),
                        skillsTalentsSelectedCareerTalentNames = emptyList(),
                        skillsTalentsRolledTalents = emptyMap(),
                        hasGeneratedWealth = false,
                    )
                    updated
                }
            }

            is CharacterCreatorEvent.ShowMessage -> {
                _state.update { it.copy(message = event.message) }
            }

            is CharacterCreatorEvent.ClearMessage -> {
                _state.update { it.copy(message = null) }
            }

            is CharacterCreatorEvent.AddExperience -> {
                _state.update { current ->
                    val (currentExp, spentExp, totalExp) = current.character.experience

                    val newCurrentExp = currentExp + event.experience
                    val newTotalExp = totalExp + event.experience

                    val updatedCharacter = current.character.copy(
                        experience = listOf(
                            newCurrentExp,
                            spentExp,
                            newTotalExp
                        )
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Experience added: ${event.experience}"
                    )
                    updated
                }
            }

            is CharacterCreatorEvent.RemoveExperience -> {
                _state.update { current ->
                    val (currentExp, spentExp, totalExp) = current.character.experience

                    // Experience cant drop below removed experience
                    val newCurrentExp = (currentExp - event.experience).coerceAtLeast(0)
                    val newTotalExp = (totalExp - event.experience).coerceAtLeast(spentExp)

                    val updatedCharacter = current.character.copy(
                        experience = listOf(
                            newCurrentExp,
                            spentExp,
                            newTotalExp
                        )
                    )

                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Experience removed: ${event.experience}"
                    )
                    updated
                }
            }

            // SPECIES
            is CharacterCreatorEvent.SetSpecies -> {

                _state.update { current ->
                    val newSpecies = event.speciesItem.name
                    val resetClassAndCareer = current.character.species != newSpecies
                    val updatedCharacter = current.character.copy(
                        species = newSpecies,
                        woundsFormula = event.speciesItem.wounds ?: current.character.woundsFormula,
                        cLass = if (resetClassAndCareer) "" else current.character.cLass,
                        career = if (resetClassAndCareer) "" else current.character.career,
                        careerLevel = if (resetClassAndCareer) "" else current.character.careerLevel,
                        careerPath = if (resetClassAndCareer) "" else current.character.careerPath,
                        status = if (resetClassAndCareer) "" else current.character.status
                    )
                    val updated = current.copy(
                        selectedSpecies = event.speciesItem,
                        selectedClass = if (resetClassAndCareer) null else current.selectedClass,
                        selectedCareer = if (resetClassAndCareer) null else current.selectedCareer,
                        rolledAttributes = if (resetClassAndCareer) emptyList() else current.rolledAttributes,
                        totalAttributes = if (resetClassAndCareer) emptyList() else current.totalAttributes,
                        character = updatedCharacter,
                        message = "Selected species: $newSpecies"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetHasRolledSpecies -> {
                _state.update { it.copy(hasRolledSpecies = event.value) }
            }

            is CharacterCreatorEvent.SetHasChosenSpecies -> {
                _state.update { it.copy(hasChosenSpecies = event.value) }
            }

            // CLASS & CAREER
            is CharacterCreatorEvent.SetClass -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        cLass = event.classItem.name
                    )
                    val updated = current.copy(
                        selectedClass = event.classItem,
                        rolledAttributes = emptyList(),
                        totalAttributes = emptyList(),
                        character = updatedCharacter,
                        message = "Selected class: ${event.classItem.name}"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetCareer -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        career = event.careerItem?.name ?: "",
                        careerPath = event.careerPathItem?.name ?: "",
                        careerLevel = event.careerPathItem?.name ?: "",
                        status = event.careerPathItem?.status ?: "",
                    )
                    val updated = current.copy(
                        selectedCareer = event.careerItem,
                        rolledAttributes = emptyList(),
                        totalAttributes = emptyList(),
                        character = updatedCharacter,
                        message = "Selected career: ${event.careerItem?.name ?: ""}"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetHasRolledClassAndCareer -> {
                _state.update { it.copy(hasRolledClassAndCareer = event.value) }
            }

            is CharacterCreatorEvent.SetHasChosenClassAndCareer -> {
                _state.update { it.copy(hasChosenClassAndCareer = event.value) }
            }

            is CharacterCreatorEvent.SetRollLocked -> {
                _state.update { it.copy(rollLocked = event.locked) }
            }

            is CharacterCreatorEvent.SetRolledFromAllocation -> {
                _state.update { it.copy(rolledFromAllocation = event.value) }
            }

            is CharacterCreatorEvent.SaveRolledAttributes -> {
                _state.value = state.value.copy(
                    rolledAttributes = event.rolled,
                    totalAttributes = event.total
                )
            }

            // ATTRIBUTES
            is CharacterCreatorEvent.SetAttributes -> {
                _state.update { currentState ->
                    val character = currentState.character
                    val totals = event.totalAttributes

                    fun tripleOrOld(index: Int, old: List<Int>): List<Int> {
                        val value = totals?.getOrNull(index)
                        return if (value != null) listOf(value, 0, value) else old
                    }

                    val maxWounds = calculateMaxWounds(character, character.woundsFormula)

                    val updatedCharacter = character.copy(
                        weaponSkill = tripleOrOld(0, character.weaponSkill),
                        ballisticSkill = tripleOrOld(1, character.ballisticSkill),
                        strength = tripleOrOld(2, character.strength),
                        toughness = tripleOrOld(3, character.toughness),
                        initiative = tripleOrOld(4, character.initiative),
                        agility = tripleOrOld(5, character.agility),
                        dexterity = tripleOrOld(6, character.dexterity),
                        intelligence = tripleOrOld(7, character.intelligence),
                        willPower = tripleOrOld(8, character.willPower),
                        fellowship = tripleOrOld(9, character.fellowship),

                        fate = event.fatePoints ?: character.fate,
                        fortune = event.fatePoints ?: character.fortune,
                        resilience = event.resiliencePoints ?: character.resilience,
                        resolve = character.resolve,

                        wounds = listOf(maxWounds, maxWounds)
                    )

                    println("Updated CharacterItem: $updatedCharacter")
                    currentState.copy(
                        character = updatedCharacter,
                        rolledAttributes = event.rolledAttributes ?: currentState.rolledAttributes,
                        totalAttributes = event.totalAttributes ?: currentState.totalAttributes,
                        message = "Attributes updated"
                    )
                }
            }

            is CharacterCreatorEvent.SetHasRolledAttributes -> {
                _state.update { it.copy(hasRolledAttributes = event.value) }
            }

            is CharacterCreatorEvent.SetHasReorderAttributes -> {
                _state.update { it.copy(hasReorderedAttributes = event.value) }
            }

            is CharacterCreatorEvent.SetHasAllocateAttributes -> {
                _state.update { it.copy(hasAllocatedAttributes = event.value) }
            }

            // SKILLS & TALENTS
            is CharacterCreatorEvent.SetSkillsAndTalents -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        basicSkills = event.speciesBasicSkills + event.careerBasicSkills,
                        advancedSkills = event.speciesAdvancedSkills + event.careerAdvancedSkills,
                        talents = event.talents
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Skills and talents selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetSkillsTalentsSelections -> {
                _state.update { cur ->
                    cur.copy(
                        skillsTalentsSelectedSkillNames3 = event.selectedSkillNames3,
                        skillsTalentsSelectedSkillNames5 = event.selectedSkillNames5,
                        skillsTalentsCareerSkillPoints = event.careerSkillPoints,
                        skillsTalentsSelectedSpeciesTalentNames = event.selectedSpeciesTalentNames,
                        skillsTalentsSelectedCareerTalentNames = event.selectedCareerTalentNames,
                        skillsTalentsRolledTalents = event.rolledTalents
                    )
                }
            }

            // TRAPPINGS & WEALTH
            is CharacterCreatorEvent.SetTrappings -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        trappings = event.trappings
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Trappings selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            is CharacterCreatorEvent.SetWealth -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        wealth = event.wealth
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        hasGeneratedWealth = true,
                        message = "Wealth selected!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            // CHARACTER DETAILS
            is CharacterCreatorEvent.SetCharacterDetails -> {
                _state.update { current ->
                    val updatedCharacter = current.character.copy(
                        name = event.name,
                        age = event.age,
                        height = event.height,
                        hair = event.hairColor,
                        eyes = event.eyeColor,
                    )
                    val updated = current.copy(
                        character = updatedCharacter,
                        message = "Character details set!"
                    )
                    println("Updated CharacterItem: $updatedCharacter")
                    updated
                }
            }

            else -> Unit
        }
    }

    private fun calculateMaxWounds(character: CharacterItem, formula: String?): Int {
        if (formula.isNullOrBlank()) return 0

        val s = character.strength.getOrNull(2) ?: 0
        val t = character.toughness.getOrNull(2) ?: 0
        val wp = character.willPower.getOrNull(2) ?: 0

        fun bonus(v: Int) = (v / 10).coerceAtLeast(0)

        val replaced = formula
            .replace("SB", bonus(s).toString())
            .replace("TB", bonus(t).toString())
            .replace("WPB", bonus(wp).toString())
            .replace(" ", "")
            .replace("(", "")
            .replace(")", "")

        return try {
            replaced.split("+").sumOf { term ->
                if (term.contains("*")) {
                    val parts = term.split("*")
                    parts.fold(1) { acc, part ->
                        acc * part.toInt()
                    }
                } else {
                    term.toInt()
                }
            }
        } catch (_: Exception) {
            0
        }
    }

}
