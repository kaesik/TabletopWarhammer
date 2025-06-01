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
    data class OnClassSelect(val id: String) : CharacterClassAndCareerEvent()
    data class SetSelectedCareer(val careerItem: CareerItem) : CharacterClassAndCareerEvent()
    data class OnCareerSelect(val id: String) : CharacterClassAndCareerEvent()

    data object RollRandomClassAndCareer : CharacterClassAndCareerEvent()
    data object SetHasRolledClassAndCareer : CharacterClassAndCareerEvent()

    data object OnNextClick : CharacterClassAndCareerEvent()
    data object OnBackClick : CharacterClassAndCareerEvent()
}
