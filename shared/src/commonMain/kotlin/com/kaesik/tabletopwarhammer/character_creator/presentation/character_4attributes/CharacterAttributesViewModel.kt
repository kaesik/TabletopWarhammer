package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.parseAttributeFormula
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()

    private var characterAttributeJob: Job? = null
    fun onEvent(event: CharacterAttributesEvent) {
        when (event) {
            is CharacterAttributesEvent.InitAttributesList -> loadAttributesList(event.speciesName)
            is CharacterAttributesEvent.InitFateAndResilience -> loadFateAndResilience(event.speciesName)
            is CharacterAttributesEvent.OnDistributeFatePointsClick -> {
                _state.value = state.value.copy(
                    showFateResilienceCard = !state.value.showFateResilienceCard
                )
            }

            is CharacterAttributesEvent.IncreaseFatePoints -> {
                if (state.value.extraPoints > 0) {
                    val newExtraPoints = state.value.extraPoints - 1
                    _state.value = state.value.copy(
                        fatePoints = state.value.fatePoints + 1,
                        extraPoints = newExtraPoints
                    )
                }
            }

            is CharacterAttributesEvent.DecreaseFatePoints -> {
                if (state.value.fatePoints > state.value.baseFatePoints) {
                    _state.value = state.value.copy(
                        fatePoints = state.value.fatePoints - 1,
                        extraPoints = state.value.extraPoints + 1
                    )
                }
            }

            is CharacterAttributesEvent.IncreaseResiliencePoints -> {
                if (state.value.extraPoints > 0) {
                    val newExtraPoints = state.value.extraPoints - 1
                    _state.value = state.value.copy(
                        resiliencePoints = state.value.resiliencePoints + 1,
                        extraPoints = newExtraPoints
                    )
                }
            }

            is CharacterAttributesEvent.DecreaseResiliencePoints -> {
                if (state.value.resiliencePoints > state.value.baseResiliencePoints) {
                    _state.value = state.value.copy(
                        resiliencePoints = state.value.resiliencePoints - 1,
                        extraPoints = state.value.extraPoints + 1
                    )
                }
            }

            else -> {}
        }
    }

    private fun loadAttributesList(speciesName: String) {
        characterAttributeJob?.cancel()
        characterAttributeJob = viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)

                val attributeList = characterCreatorClient.getAttributes()
                val species = characterCreatorClient.getSpeciesDetails(speciesName)

                val parsedAttributes = listOf(
                    parseAttributeFormula(species.weaponSkill),
                    parseAttributeFormula(species.ballisticSkill),
                    parseAttributeFormula(species.strength),
                    parseAttributeFormula(species.toughness),
                    parseAttributeFormula(species.agility),
                    parseAttributeFormula(species.dexterity),
                    parseAttributeFormula(species.intelligence),
                    parseAttributeFormula(species.willpower),
                    parseAttributeFormula(species.fellowship),
                    parseAttributeFormula(species.initiative),
                )

                val baseAttributes = parsedAttributes.map { it.baseValue }
                val diceThrows = parsedAttributes.map { it.diceThrow }

                _state.value = state.value.copy(
                    attributeList = attributeList,
                    species = species,
                    totalAttributeValues = baseAttributes,
                    diceThrows = diceThrows,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun loadFateAndResilience(speciesName: String) {
        viewModelScope.launch {
            try {
                val species = characterCreatorClient.getSpeciesDetails(speciesName)

                val baseFate = species.fatePoints?.toIntOrNull() ?: 0
                val baseResilience = species.resilience?.toIntOrNull() ?: 0
                val extraPoints = species.extraPoints?.toIntOrNull() ?: 0

                _state.value = state.value.copy(
                    baseFatePoints = baseFate,
                    baseResiliencePoints = baseResilience,
                    fatePoints = baseFate,
                    resiliencePoints = baseResilience,
                    extraPoints = extraPoints
                )
            } catch (e: Exception) {
                _state.value = state.value.copy(error = e.message)
            }
        }
    }
}
