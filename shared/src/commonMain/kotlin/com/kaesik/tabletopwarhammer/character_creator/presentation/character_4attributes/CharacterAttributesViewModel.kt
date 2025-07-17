package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.parseAttributeFormula
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()
    var onAllRolled: (() -> Unit)? = null

    private var attributeJob: Job? = null

    fun onEvent(event: CharacterAttributesEvent) {
        when (event) {
            is CharacterAttributesEvent.InitAttributesList -> {
                loadAttributesList(
                    speciesName = event.speciesName,
                    rolled = event.rolled,
                    total = event.total
                )
            }

            is CharacterAttributesEvent.InitFateAndResilience -> loadFateAndResilience(event.speciesName)
            is CharacterAttributesEvent.OnDistributeFatePointsClick -> {
                _state.value = state.value.copy(
                    showFateResilienceCard = !state.value.showFateResilienceCard
                )
            }

            is CharacterAttributesEvent.IncreaseFatePoints -> {
                if (state.value.extraPoints > 0) {
                    _state.value = state.value.copy(
                        fatePoints = state.value.fatePoints + 1,
                        extraPoints = state.value.extraPoints - 1
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
                    _state.value = state.value.copy(
                        resiliencePoints = state.value.resiliencePoints + 1,
                        extraPoints = state.value.extraPoints - 1
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

            is CharacterAttributesEvent.ToggleReorderMode -> {
                _state.value = state.value.copy(isReordering = !state.value.isReordering)
            }

            is CharacterAttributesEvent.SwapAttributes -> {
                val rolled = state.value.rolledDiceResults.toMutableList()
                val total = state.value.totalAttributeValues.toMutableList()

                rolled.swap(event.fromIndex, event.toIndex)
                total.swap(event.fromIndex, event.toIndex)

                _state.value = state.value.copy(
                    rolledDiceResults = rolled,
                    totalAttributeValues = total
                )
            }

            is CharacterAttributesEvent.UpdateDiceThrowOrder -> {
                val newRolled = event.reordered
                val newTotal =
                    state.value.baseAttributeValues.zip(newRolled) { base, roll -> base + roll }

                _state.value = state.value.copy(
                    rolledDiceResults = newRolled,
                    totalAttributeValues = newTotal
                )
            }

            else -> {}
        }
    }

    private fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
    }

    private fun loadAttributesList(
        speciesName: String,
        rolled: List<Int>? = null,
        total: List<Int>? = null
    ) {
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

                val rolledValues = rolled ?: List(baseAttributes.size) { 0 }
                val totalValues = total ?: baseAttributes

                _state.value = state.value.copy(
                    attributeList = attributeList,
                    species = species,
                    baseAttributeValues = baseAttributes,
                    diceThrows = diceThrows,
                    rolledDiceResults = rolledValues,
                    totalAttributeValues = totalValues,
                    isLoading = false
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
                val species = characterCreatorClient.getSpeciesDetails(speciesName)

                _state.value = state.value.copy(
                    baseFatePoints = species.fatePoints?.toIntOrNull() ?: 0,
                    baseResiliencePoints = species.resilience?.toIntOrNull() ?: 0,
                    fatePoints = species.fatePoints?.toIntOrNull() ?: 0,
                    resiliencePoints = species.resilience?.toIntOrNull() ?: 0,
                    extraPoints = species.extraPoints?.toIntOrNull() ?: 0
                )
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
        val regex = Regex("(\\d+)d(\\d+)")
        val match = regex.find(diceNotation)
        val rollSum = if (match != null) {
            val (count, sides) = match.destructured
            (1..count.toInt()).sumOf { (1..sides.toInt()).random() }
        } else 0

        rolled[notRolledIndex] = rollSum
        println("Rolled dice at index $notRolledIndex: $rollSum")
        updateRolledDice(rolled)
    }

    private fun rollAllDice() {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val diceNotations = state.value.diceThrows

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

        updateRolledDice(rolled)
    }

    private fun updateRolledDice(rolled: List<Int>) {
        val prevState = state.value

        val totalValues = prevState.baseAttributeValues.zip(rolled) { base, roll -> base + roll }
        val allRolled = rolled.all { it > 0 }

        val shouldGrantXp = allRolled && !prevState.hasReceivedXpForRolling

        _state.value = prevState.copy(
            rolledDiceResults = rolled,
            totalAttributeValues = totalValues,
            hasRolledDice = rolled.any { it > 0 },
            hasRolledAllDice = allRolled,
            hasReceivedXpForRolling = prevState.hasReceivedXpForRolling || allRolled
        )

        if (shouldGrantXp) {
            onAllRolled?.invoke()
        }
    }

    fun restoreRolledAttributes(rolled: List<Int>, total: List<Int>) {
        val allRolled = rolled.all { it > 0 }
        _state.value = _state.value.copy(
            rolledDiceResults = rolled,
            totalAttributeValues = total,
            hasRolledDice = rolled.any { it > 0 },
            hasRolledAllDice = allRolled,
            hasReceivedXpForRolling = _state.value.hasReceivedXpForRolling || allRolled
        )
    }
}
