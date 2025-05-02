package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterItem
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorEvent
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val characterCreatorViewModel: CharacterCreatorViewModel,
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterSkillsAndTalentsState())
    val state = _state.asStateFlow()

    private var skillsJob: Job? = null
    private var talentsJob: Job? = null
    private var characterSkillsAndTalentsJob: Job? = null

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        when (event) {
            is CharacterSkillsAndTalentsEvent.InitSkillsList -> {
                val character = characterCreatorViewModel.state.value.character
                loadSkillsList(
                    speciesName = character.species,
                    careerPathName = character.careerPath,
                )
            }

            is CharacterSkillsAndTalentsEvent.InitTalentsList -> {
                val character = characterCreatorViewModel.state.value.character
                loadTalentsList(
                    speciesName = character.species,
                    careerPathName = character.careerPath,
                )
            }

            is CharacterSkillsAndTalentsEvent.OnSkillChecked -> {
                onSkillChecked(event.skill, event.isChecked)
            }

            CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick -> {
                onSpeciesOrCareerClick()
            }

            CharacterSkillsAndTalentsEvent.OnNextClick -> {
                onNextClick()
            }
        }
    }

    private fun onSkillChecked(
        skill: SkillItem,
        isChecked: Boolean
    ) {
        _state.update { current ->
            val updatedSelected = if (isChecked) {
                current.selectedSkills + skill
            } else {
                current.selectedSkills - skill
            }
            current.copy(selectedSkills = updatedSelected)
        }
    }

    private fun onSpeciesOrCareerClick() {
        characterSkillsAndTalentsJob?.cancel()
        characterSkillsAndTalentsJob = viewModelScope.launch {
            if (state.value.speciesOrCareer == SpeciesOrCareer.SPECIES) {
                _state.update { it.copy(speciesOrCareer = SpeciesOrCareer.CAREER) }

                val character = characterCreatorViewModel.state.value.character
                loadSkillsList(
                    speciesName = character.species,
                    careerPathName = character.careerPath,
                )
                loadTalentsList(
                    speciesName = character.species,
                    careerPathName = character.careerPath,
                )
            }
        }
    }

    private fun onNextClick() {
        characterSkillsAndTalentsJob?.cancel()
        characterSkillsAndTalentsJob = viewModelScope.launch {
            val currentCharacter = characterCreatorViewModel.state.value.character

            val speciesSkillsRaw = state.value.skillList.getOrNull(0) ?: emptyList()
            val careerSkillsRaw = state.value.skillList.getOrNull(1) ?: emptyList()

            val speciesTalentsRaw = state.value.talentList.getOrNull(0) ?: emptyList()
            val careerTalentsRaw = state.value.talentList.getOrNull(1) ?: emptyList()

            val mappedBasicSkills = mapSkills(
                skills = speciesSkillsRaw + careerSkillsRaw,
                character = currentCharacter,
                basicOnly = true
            )
            val mappedAdvancedSkills = mapSkills(
                skills = speciesSkillsRaw + careerSkillsRaw,
                character = currentCharacter,
                basicOnly = false
            )

            val mappedTalents = mapTalents(
                speciesTalentsRaw + careerTalentsRaw
            )

            characterCreatorViewModel.onEvent(
                CharacterCreatorEvent.SetSkillsAndTalents(
                    basicSkills = mappedBasicSkills,
                    advancedSkills = mappedAdvancedSkills,
                    talents = mappedTalents,
                )
            )
        }
    }

    private fun loadSkillsList(
        speciesName: String,
        careerPathName: String,
    ) {
        skillsJob?.cancel()
        skillsJob = viewModelScope.launch {
            val skillList = characterCreatorClient.getSkills(
                speciesName = speciesName,
                careerPathName = careerPathName,
            )
            _state.update {
                it.copy(skillList = skillList)
            }
        }
    }

    private fun loadTalentsList(
        speciesName: String,
        careerPathName: String,
    ) {
        talentsJob?.cancel()
        talentsJob = viewModelScope.launch {
            val talentList = characterCreatorClient.getTalents(
                speciesName = speciesName,
                careerPathName = careerPathName,
            )
            _state.update {
                it.copy(talentList = talentList)
            }
        }
    }

    private fun mapSkills(
        skills: List<SkillItem>,
        character: CharacterItem,
        basicOnly: Boolean
    ): List<List<String>> {
        return skills
            .filter { (it.isBasic == true) == basicOnly }
            .map { skill ->
                val attributeValue = getAttributeValue(character, skill.attribute.orEmpty())

                listOf(
                    skill.name,
                    skill.attribute.orEmpty(),
                    "0",
                    attributeValue.toString(),
                )
            }
    }

    private fun mapTalents(
        talents: List<TalentItem>
    ): List<List<String>> {
        return talents.map { talent ->
            listOf(
                talent.name,
                talent.source.orEmpty(),
                talent.page?.toString() ?: ""
            )
        }
    }

    private fun getAttributeValue(
        character: CharacterItem,
        attributeName: String
    ): Int {
        return when (attributeName) {
            "Weapon Skill" -> character.weaponSkill.firstOrNull() ?: 0
            "Ballistic Skill" -> character.ballisticSkill.firstOrNull() ?: 0
            "Strength" -> character.strength.firstOrNull() ?: 0
            "Toughness" -> character.toughness.firstOrNull() ?: 0
            "Initiative" -> character.initiative.firstOrNull() ?: 0
            "Agility" -> character.agility.firstOrNull() ?: 0
            "Dexterity" -> character.dexterity.firstOrNull() ?: 0
            "Intelligence" -> character.intelligence.firstOrNull() ?: 0
            "Willpower" -> character.willPower.firstOrNull() ?: 0
            "Fellowship" -> character.fellowship.firstOrNull() ?: 0
            else -> 0
        }
    }
}
