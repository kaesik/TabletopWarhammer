package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.components.parseAttributeFormula
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource
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
    private val libraryLocalDataSource: LibraryLocalDataSource,
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

            // Initialize the state from creator flags
            is CharacterAttributesEvent.InitFromCreatorFlags -> {
                _state.update { it.copy(rolledFromAllocation = event.rolledFromAllocation) }
            }

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

            // Roll fate and resilience points
            is CharacterAttributesEvent.RollFateAndResilience -> rollFateAndResilience()

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
            is CharacterAttributesEvent.AllocateMax -> allocateMax(event.index)
            is CharacterAttributesEvent.IncrementAllocate -> incrementAllocate(event.index)
            is CharacterAttributesEvent.DecrementAllocate -> decrementAllocate(event.index)
            CharacterAttributesEvent.ConfirmAllocation -> confirmAllocation()
            CharacterAttributesEvent.CancelAllocation -> cancelAllocation()

            else -> Unit
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
                        hasRolledAllDice = rolledValues.all { v -> v > 0 },
                        allocatedDiceResults = List(baseAttributes.size) { 0 },
                        allocatePointsLeft = 100,
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
                val res = event.resilience ?: baseRes
                val spent = (fate - baseFate).coerceAtLeast(0) + (res - baseRes).coerceAtLeast(0)
                val extra = (baseExtra - spent).coerceAtLeast(0)

                _state.update {
                    it.copy(
                        baseFatePoints = baseFate,
                        baseResiliencePoints = baseRes,
                        fatePoints = fate,
                        resiliencePoints = res,
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
            if (true) libraryLocalDataSource.getAllAttributes()
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
            if (true) libraryLocalDataSource.getSpecies(speciesName)
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
        val rolled =
            // If the dice were rolled from allocation, start with zeroes
            if (state.value.rolledFromAllocation) MutableList(state.value.baseAttributeValues.size) { 0 }
            else state.value.rolledDiceResults.toMutableList()

        val idx = rolled.indexOfFirst { it == 0 }
        if (idx == -1) return

        // If there are no more dice to roll, return early
        val diceNotation = state.value.diceThrows.getOrNull(idx) ?: return
        rolled[idx] = rollDiceSum(diceNotation)
        applyRolledResults(rolled)
    }

    private fun onRollAllDice() {
        val rolled =
            // If the dice were rolled from allocation, start with zeroes
            if (state.value.rolledFromAllocation) MutableList(state.value.baseAttributeValues.size) { 0 }
            else state.value.rolledDiceResults.toMutableList()

        val notations = state.value.diceThrows

        // If there are no dice to roll, return early
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
        val base = state.value.baseAttributeValues
        val total = base.zip(rolled) { b, r -> b + r }
        _state.update {
            it.copy(
                rolledDiceResults = rolled,
                totalAttributeValues = total,
                hasRolledAllDice = rolled.all { v -> v > 0 },
                // Reset allocation state when rolling new dice
                canAllocateAttributes = false,
                allocatedDiceResults = List(base.size) { 0 },
                allocatePointsLeft = 100,
                // Reset reordering state when rolling new dice
                canReorderAttributes = false,
                rolledFromAllocation = false
            )
        }
    }

    private fun rollFateAndResilience() {
        val baseFate = state.value.baseFatePoints
        val baseRes = state.value.baseResiliencePoints

        // Calculate how many points are already allocated to fate and resilience
        val alreadyAllocatedToFate = (state.value.fatePoints - baseFate).coerceAtLeast(0)
        val alreadyAllocatedToRes = (state.value.resiliencePoints - baseRes).coerceAtLeast(0)
        val baseExtra = alreadyAllocatedToFate + alreadyAllocatedToRes + state.value.extraPoints

        // Calculate the extra points available for distribution
        val extra = baseExtra.coerceAtLeast(0)

        // Randomly distribute the extra points between fate and resilience
        val fateBonus = (0..extra).random()
        val resBonus = extra - fateBonus

        // Update the state with the new fate and resilience points
        _state.update {
            it.copy(
                fatePoints = baseFate + fateBonus,
                resiliencePoints = baseRes + resBonus,
                extraPoints = 0
            )
        }
    }

    // FUNCTIONS FOR REORDERING
    private fun onToggleReorderMode() {
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
        _state.update { it.copy(rolledDiceResults = rolled, totalAttributeValues = total) }
    }

    private fun onUpdateDiceThrowOrder(event: CharacterAttributesEvent.UpdateDiceThrowOrder) {
        val newRolled = event.reordered
        val newTotal = state.value.baseAttributeValues.zip(newRolled) { b, r -> b + r }
        _state.update { it.copy(rolledDiceResults = newRolled, totalAttributeValues = newTotal) }
    }

    // FUNCTIONS FOR ALLOCATING
    private val minAllocate = 4
    private val maxAllocate = 18
    private val totalAllocate = 100

    private fun onToggleAllocationMode() {
        val n =
            state.value.attributeList.size.takeIf { it > 0 } ?: state.value.baseAttributeValues.size
        if (n == 0) return

        _state.update { current ->
            val entering = !current.canAllocateAttributes
            val currentAllocate = when {
                current.allocatedDiceResults.size == n ->
                    current.allocatedDiceResults.map { it.coerceIn(minAllocate, maxAllocate) }

                else -> List(n) { minAllocate }
            }

            val left = (totalAllocate - currentAllocate.sum()).coerceAtLeast(0)

            current.copy(
                canAllocateAttributes = entering,
                allocatedDiceResults = currentAllocate,
                allocatePointsLeft = left,
                canReorderAttributes = false,
            )
        }
    }

    private fun allocateMax(index: Int) {
        if (!state.value.canAllocateAttributes) return
        if (index !in state.value.allocatedDiceResults.indices) return
        if (state.value.allocatePointsLeft <= 0) return

        val list = state.value.allocatedDiceResults.toMutableList()
        val add = minOf(maxAllocate - list[index], state.value.allocatePointsLeft)
        if (add <= 0) return

        list[index] = list[index] + add
        val left = (totalAllocate - list.sum()).coerceAtLeast(0)

        _state.update {
            it.copy(
                allocatedDiceResults = list,
                allocatePointsLeft = left
            )
        }
    }

    private fun incrementAllocate(index: Int) {
        if (!state.value.canAllocateAttributes) return
        if (index !in state.value.allocatedDiceResults.indices) return
        if (state.value.allocatePointsLeft <= 0) return

        val list = state.value.allocatedDiceResults.toMutableList()
        if (list[index] >= maxAllocate) return

        list[index] = list[index] + 1
        val left = (totalAllocate - list.sum()).coerceAtLeast(0)
        _state.update {
            it.copy(
                allocatedDiceResults = list,
                allocatePointsLeft = left
            )
        }
    }

    private fun decrementAllocate(index: Int) {
        if (!state.value.canAllocateAttributes) return
        if (index !in state.value.allocatedDiceResults.indices) return
        if (state.value.allocatedDiceResults[index] <= 0) return

        val list = state.value.allocatedDiceResults.toMutableList()
        if (list[index] <= minAllocate) return

        list[index] = list[index] - 1
        val left = (totalAllocate - list.sum()).coerceAtLeast(0)

        _state.update {
            it.copy(
                allocatedDiceResults = list,
                allocatePointsLeft = left
            )
        }
    }

    private fun confirmAllocation() {
        if (!state.value.canAllocateAttributes) return
        if (state.value.allocatePointsLeft != 0) return


        val newRolled = state.value.allocatedDiceResults
        val newTotal = state.value.baseAttributeValues.zip(newRolled) { b, r -> b + r }

        _state.update {
            it.copy(
                rolledDiceResults = newRolled,
                totalAttributeValues = newTotal,
                hasRolledAllDice = true,
                canAllocateAttributes = false,
                rolledFromAllocation = true
            )
        }
    }

    private fun cancelAllocation() {
        _state.update { it.copy(canAllocateAttributes = false) }
    }
}
