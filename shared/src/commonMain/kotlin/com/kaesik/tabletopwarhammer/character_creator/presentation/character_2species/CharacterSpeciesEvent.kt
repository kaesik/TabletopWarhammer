package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

sealed class CharacterSpeciesEvent {
    data object InitSpeciesList : CharacterSpeciesEvent()
    data object OnSpeciesSelect : CharacterSpeciesEvent()
    data object OnNextClick : CharacterSpeciesEvent()
}
