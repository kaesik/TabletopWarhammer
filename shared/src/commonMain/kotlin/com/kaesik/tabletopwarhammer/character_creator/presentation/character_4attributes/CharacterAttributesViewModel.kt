package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.parseAttributeFormula
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.DataException
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException
import kotlin.Int as Int1

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()

    private var attributeJob: Job? = null

    fun onEvent(event: CharacterAttributesEvent) {
        when (event) {
            // Initialize the attributes list depending on the species name
            is CharacterAttributesEvent.InitAttributesList -> loadAttributesList(event)

            // Initialize fate and resilience points based on the species name
            is CharacterAttributesEvent.InitFateAndResilience -> loadFateAndResilience(event)

            // Show or hide the fate resilience card
            is CharacterAttributesEvent.OnDistributeFatePointsClick -> onToggleFateResilienceCard()

            // Increase fate points if there are extra points available
            is CharacterAttributesEvent.IncreaseFatePoints -> {
                if (state.value.extraPoints > 0) {
                    _state.value = state.value.copy(
                        fatePoints = state.value.fatePoints + 1,
                        extraPoints = state.value.extraPoints - 1
                    )
                }
            }

            // Decrease fate points if they are above the base value
            is CharacterAttributesEvent.DecreaseFatePoints -> {
                if (state.value.fatePoints > state.value.baseFatePoints) {
                    _state.value = state.value.copy(
                        fatePoints = state.value.fatePoints - 1,
                        extraPoints = state.value.extraPoints + 1
                    )
                }
            }

            // Increase resilience points if there are extra points available
            is CharacterAttributesEvent.IncreaseResiliencePoints -> {
                if (state.value.extraPoints > 0) {
                    _state.value = state.value.copy(
                        resiliencePoints = state.value.resiliencePoints + 1,
                        extraPoints = state.value.extraPoints - 1
                    )
                }
            }

            // Decrease resilience points if they are above the base value
            is CharacterAttributesEvent.DecreaseResiliencePoints -> {
                if (state.value.resiliencePoints > state.value.baseResiliencePoints) {
                    _state.value = state.value.copy(
                        resiliencePoints = state.value.resiliencePoints - 1,
                        extraPoints = state.value.extraPoints + 1
                    )
                }
            }

            // Handle various events related to rolling attributes
            CharacterAttributesEvent.RollOneDice -> onRollOneDice()
            CharacterAttributesEvent.RollAllDice -> onRollAllDice()

            CharacterAttributesEvent.ToggleReorderMode -> onToggleReorderMode()

            is CharacterAttributesEvent.ReorderAttributes -> onReorderAttributes(event)

            is CharacterAttributesEvent.UpdateDiceThrowOrder -> onUpdateDiceThrowOrder(event)

            CharacterAttributesEvent.OnNextClick -> onAttributesNext()

            CharacterAttributesEvent.OnRandomSelectionConsumed -> _state.update {
                it.copy(pendingRandomSelection = null)
            }

            CharacterAttributesEvent.OnAttributesSelectionConsumed -> _state.update {
                it.copy(pendingAttributesSelection = null)
            }

            else -> {}
        }
    }

    private fun <T> MutableList<T>.swap(i: Int1, j: Int1) {
        val tmp = this[i]
        this[i] = this[j]
        this[j] = tmp
    }

    private fun loadAttributesList(event: CharacterAttributesEvent.InitAttributesList) {
        attributeJob?.cancel()
        attributeJob = viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true, error = null)

                val attributeList = fetchAttributeList()
                val species = fetchSpecies(event.speciesName)

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

                val rolledValues = event.rolled ?: List(baseAttributes.size) { 0 }
                val totalValues = event.total ?: baseAttributes.zip(rolledValues) { base, rolled ->
                    base + rolled
                }

                _state.update {
                    it.copy(
                        attributeList = attributeList,
                        species = species,
                        baseAttributeValues = baseAttributes,
                        diceThrows = diceThrows,
                        rolledDiceResults = rolledValues,
                        totalAttributeValues = totalValues,
                        hasRolledAttributes = rolledValues.any { v -> v > 0 },
                        hasRolledAllDice = rolledValues.all { v -> v > 0 },
                        isLoading = false
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(isLoading = false, error = DataError.Local.UNKNOWN) }
            }
        }
    }

    private fun loadFateAndResilience(event: CharacterAttributesEvent.InitFateAndResilience) {
        viewModelScope.launch {
            try {
                val species = fetchSpecies(event.speciesName)
                val baseFate = species.fatePoints?.toIntOrNull() ?: 0
                val baseRes = species.resilience?.toIntOrNull() ?: 0
                val extra = species.extraPoints?.toIntOrNull() ?: 0

                _state.update {
                    it.copy(
                        baseFatePoints = baseFate,
                        baseResiliencePoints = baseRes,
                        fatePoints = baseFate,
                        resiliencePoints = baseRes,
                        extraPoints = extra
                    )
                }
            } catch (_: Exception) {
                _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            }
        }
    }

    private suspend fun fetchAttributeList(): List<AttributeItem> {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getAllAttributes()
            else characterCreatorClient.getAllAttributes()

        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }; emptyList()
        } catch (_: CancellationException) {
            emptyList()
        } catch (_: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            emptyList()
        }
    }

    private suspend fun fetchSpecies(speciesName: String): SpeciesItem {
        return try {
            // PLACEHOLDER: Replace with actual condition to determine data source
            if (true) libraryDataSource.getSpecies(speciesName)
            else characterCreatorClient.getSpeciesDetails(speciesName)

        } catch (e: DataException) {
            _state.update { it.copy(error = e.error) }
            SpeciesItem.default()
        } catch (e: CancellationException) {
            SpeciesItem.default()
        } catch (e: Exception) {
            _state.update { it.copy(error = DataError.Local.UNKNOWN) }
            SpeciesItem.default()
        }
    }

    private fun onToggleFateResilienceCard() {
        _state.update { it.copy(showFateResilienceCard = !it.showFateResilienceCard) }
    }

    // FUNCTIONS FOR ROLLING DICE
    private fun onRollOneDice() {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val idx = rolled.indexOfFirst { it == 0 }
        if (idx == -1) return

        val diceNotation = state.value.diceThrows.getOrNull(idx) ?: return
        rolled[idx] = rollDiceSum(diceNotation)
        applyRolledResults(rolled)
    }

    private fun onRollAllDice() {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val notations = state.value.diceThrows
        for (i in rolled.indices) if (rolled[i] == 0) rolled[i] = rollDiceSum(notations[i])
        applyRolledResults(rolled)
    }

    private fun rollDiceSum(diceNotation: String): Int1 {
        val regex = Regex("(\\d+)d(\\d+)")
        val m = regex.find(diceNotation) ?: return 0
        val (count, sides) = m.destructured
        return (1..count.toInt()).sumOf { (1..sides.toInt()).random() }
    }

    private fun applyRolledResults(rolled: List<Int1>) {
        val prev = state.value
        val total = prev.baseAttributeValues.zip(rolled) { b, r -> b + r }
        val allRolled = rolled.all { it > 0 }
        val shouldGrantXp = allRolled && !prev.hasReceivedXpForRolling

        _state.update {
            it.copy(
                rolledDiceResults = rolled,
                totalAttributeValues = total,
                hasRolledAttributes = rolled.any { v -> v > 0 },
                hasRolledAllDice = allRolled,
                hasReceivedXpForRolling = it.hasReceivedXpForRolling || allRolled,
                pendingRandomSelection = if (shouldGrantXp)
                    AttributesSelection(
                        exp = 50,
                        message = "You gained 50 XP for rolling attributes!"
                    )
                else it.pendingRandomSelection
            )
        }
    }

    // FUNCTIONS FOR REORDERING
    private fun onToggleReorderMode() {
        _state.update { it.copy(canReorderAttributes = !it.canReorderAttributes) }
    }

    private fun onReorderAttributes(event: CharacterAttributesEvent.ReorderAttributes) {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val total = state.value.totalAttributeValues.toMutableList()
        rolled.swap(event.fromIndex, event.toIndex)
        total.swap(event.fromIndex, event.toIndex)
        _state.update { it.copy(rolledDiceResults = rolled, totalAttributeValues = total) }
    }

    private fun onUpdateDiceThrowOrder(event: CharacterAttributesEvent.UpdateDiceThrowOrder) {
        val newRolled = event.reordered
        val newTotal = state.value.baseAttributeValues.zip(newRolled) { base, roll -> base + roll }
        _state.update { it.copy(rolledDiceResults = newRolled, totalAttributeValues = newTotal) }
    }

    private fun onAttributesNext() {
        if (!state.value.isLoading
            && state.value.extraPoints == 0
            && state.value.totalAttributeValues.isNotEmpty()
        ) {
            _state.update {
                it.copy(
                    pendingAttributesSelection = AttributesSelection(
                        totalAttributes = state.value.totalAttributeValues,
                        fatePoints = state.value.fatePoints,
                        resiliencePoints = state.value.resiliencePoints,
                    )
                )
            }
        }
    }

    private fun restoreRolledAttributes(rolled: List<Int1>, total: List<Int1>) {
        val allRolled = rolled.all { it > 0 }
        _state.value = _state.value.copy(
            rolledDiceResults = rolled,
            totalAttributeValues = total,
            hasRolledAttributes = rolled.any { it > 0 },
            hasRolledAllDice = allRolled,
            hasReceivedXpForRolling = _state.value.hasReceivedXpForRolling || allRolled
        )
    }
}
