package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

sealed class CharacterAttributesEvent {
    data class InitAttributesList(
        val speciesName: String,
        val rolled: List<Int>?,
        val total: List<Int>?
    ) : CharacterAttributesEvent()
    data class InitFateAndResilience(val speciesName: String) : CharacterAttributesEvent()

    data object OnDistributeFatePointsClick : CharacterAttributesEvent()

    data object IncreaseFatePoints : CharacterAttributesEvent()
    data object DecreaseFatePoints : CharacterAttributesEvent()
    data object IncreaseResiliencePoints : CharacterAttributesEvent()
    data object DecreaseResiliencePoints : CharacterAttributesEvent()

    data object RollOneDice : CharacterAttributesEvent()
    data object RollAllDice : CharacterAttributesEvent()

    data object OnNextClick : CharacterAttributesEvent()
}
