package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem

sealed class CharacterClassAndCareerEvent {
    data object InitClassList : CharacterClassAndCareerEvent()
    data class InitCareerList(
        val speciesName: String,
        val className: String
    ) : CharacterClassAndCareerEvent()

    data class SetSelectedClass(val classItem: ClassItem) : CharacterClassAndCareerEvent()
    data class SetSelectedCareer(val careerItem: CareerItem) : CharacterClassAndCareerEvent()
    data class SetSelectingClass(val canSelectClass: Boolean) : CharacterClassAndCareerEvent()
    data class SetSelectingCareer(val canSelectCareer: Boolean) : CharacterClassAndCareerEvent()

    data class OnClassSelect(
        val id: String,
        val currentClassId: String? = null
    ) : CharacterClassAndCareerEvent()

    data class OnCareerSelect(
        val id: String,
        val currentCareerId: String? = null
    ) : CharacterClassAndCareerEvent()

    data class OnClassAndCareerRoll(
        val speciesName: String,
        val currentClassId: String? = null,
        val currentCareerId: String? = null
    ) : CharacterClassAndCareerEvent()

    data object OnClassSelectionConsumed : CharacterClassAndCareerEvent()
    data object OnCareerSelectionConsumed : CharacterClassAndCareerEvent()
    data object OnRandomSelectionConsumed : CharacterClassAndCareerEvent()

    data object OnNextClick : CharacterClassAndCareerEvent()
}
