package com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species

import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

sealed class CharacterSpeciesEvent {
    data object InitSpeciesList : CharacterSpeciesEvent()

    data class SetSelectedSpecies(val speciesItem: SpeciesItem) : CharacterSpeciesEvent()
    data class SetSelectingSpecies(val canSelectSpecies: Boolean) : CharacterSpeciesEvent()

    data class OnSpeciesSelect(
        val id: String,
        val currentSpeciesId: String? = null
    ) : CharacterSpeciesEvent()

    data class OnSpeciesRoll(
        val currentSpeciesId: String? = null
    ) : CharacterSpeciesEvent()

    data object OnSpeciesSelectionConsumed : CharacterSpeciesEvent()
    data object OnRandomSpeciesConsumed : CharacterSpeciesEvent()

    data object OnNextClick : CharacterSpeciesEvent()
}
