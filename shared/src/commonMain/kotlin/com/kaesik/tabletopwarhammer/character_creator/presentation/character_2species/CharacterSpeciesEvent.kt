package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

sealed class CharacterSpeciesEvent {
    data object InitSpeciesList : CharacterSpeciesEvent()

    data class OnSpeciesSelect(val id: String) : CharacterSpeciesEvent()
    data object RollRandomSpecies : CharacterSpeciesEvent()

    data object OnNextClick : CharacterSpeciesEvent()
    data object OnBackClick : CharacterSpeciesEvent()
}
