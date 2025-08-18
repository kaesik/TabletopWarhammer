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

class CharacterAttributesViewModel(
    private val characterCreatorClient: CharacterCreatorClient,
    private val libraryDataSource: LibraryDataSource,
) : ViewModel() {
    private val _state = MutableStateFlow(CharacterAttributesState())
    val state = _state.asStateFlow()

    private var attributeJob: Job? = null

    fun onEvent(event: CharacterAttributesEvent) {
        when (event) {
            // INITIALIZING
            // Initialize the attributes list depending on the species name
            is CharacterAttributesEvent.InitAttributesList -> loadAttributesList(event)

            // Initialize fate and resilience points based on the species name
            is CharacterAttributesEvent.InitFateAndResilience -> loadFateAndResilience(event)

            // FATE AND RESILIENCE POINTS
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

            //ROLL
            // Handle various events related to rolling attributes
            CharacterAttributesEvent.RollOneDice -> onRollOneDice()
            CharacterAttributesEvent.RollAllDice -> onRollAllDice()

            //REORDER
            // Toggle the reorder mode for attributes
            CharacterAttributesEvent.ToggleReorderMode -> onToggleReorderMode()

            // Reorder attributes based on the event indices
            is CharacterAttributesEvent.ReorderAttributes -> onReorderAttributes(event)

            // Update the order of dice throws based on the event
            is CharacterAttributesEvent.UpdateDiceThrowOrder -> onUpdateDiceThrowOrder(event)

            // ALLOCATE
            CharacterAttributesEvent.ToggleAllocationMode -> onToggleAllocationMode()
            is CharacterAttributesEvent.IncrementAllocate -> incrementAllocate(event.index)
            is CharacterAttributesEvent.DecrementAllocate -> decrementAllocate(event.index)
            CharacterAttributesEvent.ConfirmAllocation -> confirmAllocation()
            CharacterAttributesEvent.CancelAllocation -> cancelAllocation()

            // NEXT
            CharacterAttributesEvent.OnNextClick -> onAttributesNext()

            // CONSUME
            CharacterAttributesEvent.OnRandomSelectionConsumed -> _state.update {
                it.copy(pendingRandomSelection = null)
            }

            CharacterAttributesEvent.OnAttributesSelectionConsumed -> _state.update {
                it.copy(pendingAttributesSelection = null)
            }

            CharacterAttributesEvent.OnFateResilienceSelectionConsumed -> _state.update {
                it.copy(pendingFateResilienceSelection = null)
            }

            else -> {}
        }
    }

    private fun <T> MutableList<T>.swap(i: Int, j: Int) {
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
                    parseAttributeFormula(species.initiative),
                    parseAttributeFormula(species.agility),
                    parseAttributeFormula(species.dexterity),
                    parseAttributeFormula(species.intelligence),
                    parseAttributeFormula(species.willpower),
                    parseAttributeFormula(species.fellowship),
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
                        allocatedDiceResults = List(baseAttributes.size) { 0 },
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
                val baseExtra = species.extraPoints?.toIntOrNull() ?: 0

                val fate = event.fate ?: baseFate
                val resilience = event.resilience ?: baseRes
                val spent =
                    (fate - baseFate).coerceAtLeast(0) + (resilience - baseRes).coerceAtLeast(0)
                val extra = (baseExtra - spent).coerceAtLeast(0)

                _state.update {
                    it.copy(
                        baseFatePoints = baseFate,
                        baseResiliencePoints = baseRes,
                        fatePoints = fate,
                        resiliencePoints = resilience,
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

    private fun rollDiceSum(diceNotation: String): Int {
        val regex = Regex("(\\d+)d(\\d+)")
        val m = regex.find(diceNotation) ?: return 0
        val (count, sides) = m.destructured
        return (1..count.toInt()).sumOf { (1..sides.toInt()).random() }
    }

    private fun applyRolledResults(rolled: List<Int>) {
        val prev = state.value
        val total = prev.baseAttributeValues.zip(rolled) { b, r -> b + r }
        val allRolled = rolled.all { it > 0 }
        val shouldGrantXp = allRolled && !prev.rollingXpApplied

        _state.update {
            it.copy(
                rolledDiceResults = rolled,
                totalAttributeValues = total,
                hasRolledAttributes = rolled.any { v -> v > 0 },
                hasRolledAllDice = allRolled,
                rollingXpApplied = it.rollingXpApplied || allRolled,
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
        if (!state.value.hasRolledAttributes) return
        _state.update {
            it.copy(
                canReorderAttributes = !it.canReorderAttributes,
                canAllocateAttributes = false
            )
        }
    }

    private fun onReorderAttributes(event: CharacterAttributesEvent.ReorderAttributes) {
        val rolled = state.value.rolledDiceResults.toMutableList()
        val total = state.value.totalAttributeValues.toMutableList()
        rolled.swap(event.fromIndex, event.toIndex)
        total.swap(event.fromIndex, event.toIndex)
        val applyPenalty = !state.value.reorderXpApplied

        _state.update {
            it.copy(
                rolledDiceResults = rolled,
                totalAttributeValues = total,
                hasReorderedAttributes = true,
                reorderXpApplied = it.reorderXpApplied || applyPenalty,
                pendingRandomSelection = if (applyPenalty)
                    AttributesSelection(exp = -25, message = "Reordered stats: −25 XP.")
                else it.pendingRandomSelection
            )
        }
    }

    private fun onUpdateDiceThrowOrder(event: CharacterAttributesEvent.UpdateDiceThrowOrder) {
        val newRolled = event.reordered
        val newTotal = state.value.baseAttributeValues.zip(newRolled) { base, roll -> base + roll }
        val applyPenalty = !state.value.reorderXpApplied

        _state.update {
            it.copy(
                rolledDiceResults = newRolled,
                totalAttributeValues = newTotal,
                hasReorderedAttributes = true,
                reorderXpApplied = it.reorderXpApplied || applyPenalty,
                pendingRandomSelection = if (applyPenalty)
                    AttributesSelection(exp = -25, message = "Reordered stats: −25 XP.")
                else it.pendingRandomSelection
            )
        }
    }

    // FUNCTIONS FOR ALLOCATING
    private fun onToggleAllocationMode() {
        val n =
            state.value.attributeList.size.takeIf { it > 0 } ?: state.value.baseAttributeValues.size
        if (n == 0) return

        _state.update { cur ->
            val entering = !cur.canAllocateAttributes
            cur.copy(
                canAllocateAttributes = entering,
                allocatedDiceResults = if (entering) List(n) { 0 } else cur.allocatedDiceResults,
//                allocatePointsLeft = if (entering) 100 else cur.allocatePointsLeft,
                allocatePointsLeft = if (entering) 1 else cur.allocatePointsLeft,
                canReorderAttributes = false
            )
        }
    }

    private fun incrementAllocate(index: Int) {
        val s = state.value
        if (!s.canAllocateAttributes) return
        if (index !in s.allocatedDiceResults.indices) return
        if (s.allocatePointsLeft <= 0) return

        val list = s.allocatedDiceResults.toMutableList()
        list[index] = list[index] + 1
        _state.update {
            it.copy(
                allocatedDiceResults = list,
                allocatePointsLeft = it.allocatePointsLeft - 1
            )
        }
    }

    private fun decrementAllocate(index: Int) {
        val s = state.value
        if (!s.canAllocateAttributes) return
        if (index !in s.allocatedDiceResults.indices) return
        if (s.allocatedDiceResults[index] <= 0) return

        val list = s.allocatedDiceResults.toMutableList()
        list[index] = list[index] - 1
        _state.update {
            it.copy(
                allocatedDiceResults = list,
                allocatePointsLeft = it.allocatePointsLeft + 1
            )
        }
    }

    private fun confirmAllocation() {
        val s = state.value
        if (!s.canAllocateAttributes) return
        if (s.allocatePointsLeft != 0) return

        val penalty = when {
            !s.hasRolledAttributes -> 0
            s.hasRolledAttributes && s.hasReorderedAttributes -> -25
            else -> -50
        }

        val newRolled = s.allocatedDiceResults
        val newTotal = s.baseAttributeValues.zip(newRolled) { b, r -> b + r }

        val applyOnce = !s.allocationXpApplied

        _state.update {
            it.copy(
                rolledDiceResults = newRolled,
                totalAttributeValues = newTotal,

                hasRolledAttributes = true,
                hasRolledAllDice = true,

                hasAllocateAttributes = true,
                canAllocateAttributes = false,
                allocationXpApplied = it.allocationXpApplied || applyOnce,

                pendingRandomSelection =
                if (applyOnce)
                    AttributesSelection(
                        exp = penalty,
                        message = when (penalty) {
                            -50 -> "Allocated 100 points after rolling: −50 XP."
                            -25 -> "Allocated 100 points after reorder: −25 XP."
                            else -> "Allocated 100 points: +0 XP."
                        }
                    )
                else it.pendingRandomSelection
            )
        }
    }

    private fun cancelAllocation() {
        _state.update { it.copy(canAllocateAttributes = false) }
    }

    // FUNCTIONS FOR NEXT
    private fun onAttributesNext() {
        if (!state.value.isLoading
            && state.value.extraPoints == 0
        ) {
            _state.update {
                it.copy(
                    pendingFateResilienceSelection = AttributesSelection(
                        fatePoints = state.value.fatePoints,
                        resiliencePoints = state.value.resiliencePoints
                    )
                )
            }
        }

        if (!state.value.isLoading
            && state.value.totalAttributeValues.isNotEmpty()
        ) {
            _state.update {
                it.copy(
                    pendingAttributesSelection = AttributesSelection(
                        totalAttributes = state.value.totalAttributeValues,
                    )
                )
            }
        }
    }
}
