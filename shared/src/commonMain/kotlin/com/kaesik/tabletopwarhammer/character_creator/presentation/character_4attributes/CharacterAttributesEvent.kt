package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

sealed class CharacterAttributesEvent {

    // ATTRIBUTES
    data class InitAttributesList(
        val speciesName: String,
        val rolled: List<Int>?,
        val total: List<Int>?
    ) : CharacterAttributesEvent()

    //FATE AND RESILIENCE
    data object OnDistributeFatePointsClick : CharacterAttributesEvent()
    data object IncreaseFatePoints : CharacterAttributesEvent()
    data object DecreaseFatePoints : CharacterAttributesEvent()
    data object IncreaseResiliencePoints : CharacterAttributesEvent()
    data object DecreaseResiliencePoints : CharacterAttributesEvent()
    data class InitFateAndResilience(
        val speciesName: String,
        val fate: Int? = null,
        val resilience: Int? = null
    ) : CharacterAttributesEvent()

    // ROLL
    data object RollOneDice : CharacterAttributesEvent()
    data object RollAllDice : CharacterAttributesEvent()

    // REORDER
    data object ToggleReorderMode : CharacterAttributesEvent()
    data class ReorderAttributes(val fromIndex: Int, val toIndex: Int) : CharacterAttributesEvent()
    data class UpdateDiceThrowOrder(val reordered: List<Int>) : CharacterAttributesEvent()

    // ALLOCATE
    data object ToggleAllocationMode : CharacterAttributesEvent()
    data class IncrementAllocate(val index: Int) : CharacterAttributesEvent()
    data class DecrementAllocate(val index: Int) : CharacterAttributesEvent()
    data object ConfirmAllocation : CharacterAttributesEvent()
    data object CancelAllocation : CharacterAttributesEvent()

    data object OnNextClick : CharacterAttributesEvent()
}
