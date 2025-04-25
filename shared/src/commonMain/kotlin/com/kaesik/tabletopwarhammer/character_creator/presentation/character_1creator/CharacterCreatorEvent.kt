package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.SpeciesItem

sealed class CharacterCreatorEvent {
    data object OnCreateCharacterSelect : CharacterCreatorEvent()
    data object OnRandomCharacterSelect : CharacterCreatorEvent()

    data class SetSpecies(val speciesItem: SpeciesItem) : CharacterCreatorEvent()
    data class SetClass(val classItem: ClassItem) : CharacterCreatorEvent()
    data class SetCareer(
        val careerItem: CareerItem,
        val careerPathItem: CareerPathItem
    ) : CharacterCreatorEvent()

}
