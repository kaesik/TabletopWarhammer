package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.parseAttributeFormula
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()

    private var attributeJob: Job? = null
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

            is CharacterAttributesEvent.RollOneDice -> rollOneDice()

            is CharacterAttributesEvent.RollAllDice -> rollAllDice()

            is CharacterAttributesEvent.AllAttributesRolled -> {
                _state.value = state.value.copy(hasReceivedXpForRolling = true)
            }

            else -> {}
        }
    }

    private fun loadAttributesList(speciesName: String) {
        attributeJob?.cancel()
        attributeJob = viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true, error = null)

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
                    baseAttributeValues = baseAttributes,
                    diceThrows = diceThrows,
                    rolledDiceResults = List(baseAttributes.size) { 0 },
                    totalAttributeValues = baseAttributes,
                    isLoading = false,
                    error = null
                )
            } catch (e: DataException) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = e.error
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = state.value.copy(
                    isLoading = false,
                    error = DataError.Local.UNKNOWN
                )
            }
        }
    }

    private fun loadFateAndResilience(speciesName: String) {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(error = null)

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
            } catch (e: DataException) {
                _state.value = state.value.copy(error = e.error)
            } catch (e: Exception) {
                e.printStackTrace()
                _state.value = state.value.copy(error = DataError.Local.UNKNOWN)
            }
        }
    }

    private fun rollOneDice() {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val notRolledIndex = rolled.indexOfFirst { it == 0 }

        if (notRolledIndex == -1) return

        val diceNotation = state.value.diceThrows.getOrNull(notRolledIndex) ?: return
        val baseValue = state.value.baseAttributeValues.getOrNull(notRolledIndex) ?: 0

        val regex = Regex("(\\d+)d(\\d+)")
        val match = regex.find(diceNotation)
        val rollSum = if (match != null) {
            val (count, sides) = match.destructured
            (1..count.toInt()).sumOf { (1..sides.toInt()).random() }
        } else 0

        rolled[notRolledIndex] = rollSum

        val totalValues = state.value.baseAttributeValues.zip(rolled) { base, roll -> base + roll }
        val allRolled = rolled.all { it > 0 }

        _state.value = state.value.copy(
            rolledDiceResults = rolled,
            totalAttributeValues = totalValues,
            hasRolledDice = rolled.any { it > 0 },
            hasRolledAllDice = allRolled
        )
    }

    private fun rollAllDice() {
        val baseValues = state.value.baseAttributeValues
        val diceNotations = state.value.diceThrows
        val rolled = state.value.rolledDiceResults.toMutableList()

        fun rollDiceSum(diceNotation: String): Int {
            val regex = Regex("(\\d+)d(\\d+)")
            val match = regex.find(diceNotation)
            return if (match != null) {
                val (count, sides) = match.destructured
                (1..count.toInt()).sumOf { (1..sides.toInt()).random() }
            } else 0
        }

        for (i in rolled.indices) {
            if (rolled[i] == 0) {
                rolled[i] = rollDiceSum(diceNotations[i])
            }
        }

        val totalValues = baseValues.zip(rolled) { base, roll -> base + roll }
        val allRolled = rolled.all { it > 0 }

        _state.value = state.value.copy(
            rolledDiceResults = rolled,
            totalAttributeValues = totalValues,
            hasRolledDice = rolled.any { it > 0 },
            hasRolledAllDice = allRolled
        )
    }
}
