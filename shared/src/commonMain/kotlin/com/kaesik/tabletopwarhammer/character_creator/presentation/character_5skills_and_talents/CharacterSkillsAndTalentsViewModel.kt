package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SpeciesOrCareer
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.mapSpecializedSkills
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.normalizeTalentGroups
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

class CharacterSkillsAndTalentsViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterSkillsAndTalentsState())
    val state = _state.asStateFlow()

    private var skillsJob: Job? = null
    private var talentsJob: Job? = null

    fun onEvent(event: CharacterSkillsAndTalentsEvent) {
        when (event) {
            // Initialize the skills list when the view model is created
            is CharacterSkillsAndTalentsEvent.InitSkillsList -> loadSkillsList(
                event.from, event.speciesName, event.careerPathName
            )

            // Initialize the talents list when the view model is created
            is CharacterSkillsAndTalentsEvent.InitTalentsList -> loadTalentsList(
                event.speciesName, event.careerPathName
            )

            // When a skill is checked or unchecked, update the selected skills list
            is CharacterSkillsAndTalentsEvent.OnSkillChecked -> updateSelectedSkills(
                event.skill, event.isChecked
            )

            // When a skill is checked or unchecked in the 3-point skills list
            is CharacterSkillsAndTalentsEvent.OnSkillChecked3 -> updateSelectedSkills3(
                event.skill, event.isChecked
            )

            // When a skill is checked or unchecked in the 5-point skills list
            is CharacterSkillsAndTalentsEvent.OnSkillChecked5 -> updateSelectedSkills5(
                event.skill, event.isChecked
            )

            // When a talent is checked or unchecked, update the selected talents list
            is CharacterSkillsAndTalentsEvent.OnTalentChecked -> handleTalentChecked(
                event.talent, event.isChecked
            )

            // When a random talent is rolled, update the rolled talents map
            is CharacterSkillsAndTalentsEvent.OnRandomTalentRolled -> {
                _state.update { current ->
                    val updatedMap = current.rolledTalents + (Pair(
                        event.groupIndex,
                        event.talentIndex
                    ) to event.rolledName)
                    current.copy(rolledTalents = updatedMap)
                }
            }

            // Toggle between species and career view
            is CharacterSkillsAndTalentsEvent.OnSpeciesOrCareerClick -> toggleSpeciesOrCareer()

            // When career skill value is changed, update the career skill points map
            is CharacterSkillsAndTalentsEvent.OnCareerSkillValueChanged -> onCareerPointsChanged(
                event.skill, event.newValue
            )

            else -> Unit
        }
    }

    private fun loadSkillsList(
        from: SpeciesOrCareer,
        speciesName: String,
        careerPathName: String,
    ) {
        skillsJob?.cancel()
        skillsJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                // Fetch skills from the appropriate data source
                val skillsList = fetchSkillsList(speciesName, careerPathName)

                // Map the fetched skills to specialized and basic skills
                val specializedSkillsList = mapSpecializedSkills(skillsList)

                _state.update {
                    when (from) {
                        SpeciesOrCareer.SPECIES -> it.copy(speciesSkillsList = specializedSkillsList)
                        SpeciesOrCareer.CAREER -> it.copy(careerSkillsList = specializedSkillsList)
                    }.copy(skillList = specializedSkillsList)
                }
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun loadTalentsList(
        speciesName: String,
        careerPathName: String,
    ) {
        talentsJob?.cancel()
        talentsJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                // Fetch talents from the appropriate data source
                val talentList = fetchTalentsList(speciesName, careerPathName)

                // Separate species and career talents
                val speciesTalentGroups = talentList.getOrNull(0) ?: emptyList()
                val careerTalentGroups = talentList.getOrNull(1) ?: emptyList()

                // Normalize talent groups to ensure consistent structure
                val processedSpeciesTalents = normalizeTalentGroups(speciesTalentGroups)
                val processedCareerTalents = normalizeTalentGroups(careerTalentGroups)

                // Auto-select talents if none are selected, prioritizing non-random talents
                val autoSelectedTalents =
                    (processedSpeciesTalents + processedCareerTalents).flatMap { group ->
                        when {
                            group.all { it.name == "Random Talent" } -> emptyList()
                            group.size > 1 -> listOf(group.first())
                            else -> group
                        }
                    }

                _state.update { current ->
                    // Update the base state with fetched and processed talents
                    val base = current.copy(
                        speciesTalentsList = processedSpeciesTalents,
                        careerTalentsList = processedCareerTalents,
                        talentList = processedSpeciesTalents + processedCareerTalents,
                        isLoading = false
                    )

                    // Save selected talents based on current view (species or career)
                    base.copy(
                        selectedTalents = if (current.speciesOrCareer == SpeciesOrCareer.SPECIES)
                            (current.selectedSpeciesTalents.ifEmpty { autoSelectedTalents })
                        else
                            (current.selectedCareerTalents.ifEmpty { autoSelectedTalents })
                    )
                }

            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private suspend fun fetchSkillsList(
        speciesName: String,
        careerPathName: String,
    ): List<List<SkillItem>> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) {
                libraryDataSource.getFilteredSkills(
                    speciesName = speciesName,
                    careerPathName = careerPathName
                )
            } else {
                characterCreatorClient.getFilteredSkills(
                    speciesName = speciesName,
                    careerPathName = careerPathName
                )
            }
        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }; emptyList()
        } catch (_: CancellationException) {
            emptyList()
        } catch (_: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            emptyList()
        }
    }

    private suspend fun fetchTalentsList(
        speciesName: String,
        careerPathName: String
    ): List<List<List<TalentItem>>> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) {
                libraryDataSource.getFilteredTalents(speciesName, careerPathName)
            } else {
                characterCreatorClient.getFilteredTalents(speciesName, careerPathName)
            }

        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }
            emptyList()
        } catch (_: CancellationException) {
            emptyList()
        } catch (_: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            emptyList()
        }
    }

    private fun toggleSpeciesOrCareer() {
        _state.update { current ->

            // Save the currently selected talents to the appropriate list
            val updatedState = when (current.speciesOrCareer) {
                SpeciesOrCareer.SPECIES -> current.copy(
                    selectedSpeciesTalents = current.selectedTalents
                )

                SpeciesOrCareer.CAREER -> current.copy(
                    selectedCareerTalents = current.selectedTalents
                )
            }

            // Toggle the view and update the selected talents accordingly
            updatedState.copy(
                // Toggle between species and career
                speciesOrCareer = if (current.speciesOrCareer == SpeciesOrCareer.SPECIES)
                    SpeciesOrCareer.CAREER else SpeciesOrCareer.SPECIES,
                // Update selected talents based on the new view
                selectedTalents = if (current.speciesOrCareer == SpeciesOrCareer.SPECIES)
                    current.selectedCareerTalents else current.selectedSpeciesTalents
            )
        }
    }

    private fun handleTalentChecked(talent: TalentItem, isChecked: Boolean) {
        _state.update { current ->
            // Extract the base name to handle "or" options correctly
            val baseName = talent.name.substringBefore(" or ").trim()
            // Remove any existing talents from the same group if selecting a new one
            val updatedTalents = if (isChecked) {
                current.selectedTalents.filterNot { it.name.startsWith(baseName) } + talent
            } else {
                current.selectedTalents - talent
            }

            current.copy(selectedTalents = updatedTalents)
        }
    }

    private fun updateSelectedSkills(skill: SkillItem, isChecked: Boolean) {
        _state.update {
            // Add or remove the skill from the selected skills list based on isChecked
            val updated = if (isChecked) it.selectedSkills + skill else it.selectedSkills - skill
            it.copy(selectedSkills = updated)
        }
    }

    private fun updateSelectedSkills3(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            // Ensure the skill is only in one of the two lists at a time
            val removedFrom5 = current.selectedSkills5.filterNot { it.name == skill.name }
            // Add or remove the skill from the 3-point skills list based on isChecked
            val updated3 = if (isChecked) current.selectedSkills3 + skill
            else current.selectedSkills3.filterNot { it.name == skill.name }

            current.copy(selectedSkills3 = updated3, selectedSkills5 = removedFrom5)
        }
    }

    private fun updateSelectedSkills5(skill: SkillItem, isChecked: Boolean) {
        _state.update { current ->
            // Ensure the skill is only in one of the two lists at a time
            val removedFrom3 = current.selectedSkills3.filterNot { it.name == skill.name }
            // Add or remove the skill from the 5-point skills list based on isChecked
            val updated5 = if (isChecked) current.selectedSkills5 + skill
            else current.selectedSkills5.filterNot { it.name == skill.name }

            current.copy(selectedSkills3 = removedFrom3, selectedSkills5 = updated5)
        }
    }

    private fun onCareerPointsChanged(skill: SkillItem, requested: Int) {
        _state.update { current ->
            // Calculate the maximum points that can be allocated to this skill
            val oldForThis = current.careerSkillPoints[skill.name] ?: 0
            val otherTotal = current.totalAllocatedPoints - oldForThis
            val maxForThis = (40 - otherTotal).coerceIn(0, 10)
            val newValue = requested.coerceIn(0, maxForThis)

            // Update the career skill points map with the new value for this skill
            val newMap = current.careerSkillPoints.toMutableMap().apply {
                this[skill.name] = newValue
            }

            current.copy(
                careerSkillPoints = newMap,
                totalAllocatedPoints = otherTotal + newValue
            )
        }
    }
}
