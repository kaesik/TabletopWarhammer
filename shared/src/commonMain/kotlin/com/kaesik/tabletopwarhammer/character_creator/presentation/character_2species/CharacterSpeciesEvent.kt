package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

sealed class CharacterSpeciesEvent {
    data object InitSpeciesList : CharacterSpeciesEvent()

    data class SetSelectedSpecies(val speciesItem: SpeciesItem) : CharacterSpeciesEvent()
    data class SetSelectingSpecies(val canSelect: Boolean) : CharacterSpeciesEvent()

    data class OnSpeciesSelect(val id: String) : CharacterSpeciesEvent()
    data class OnSpeciesChange(val id: String) : CharacterSpeciesEvent()
    data object OnSpeciesRoll : CharacterSpeciesEvent()

    data object OnNextClick : CharacterSpeciesEvent()
}
