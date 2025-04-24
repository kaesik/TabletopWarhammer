package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

sealed class CharacterClassAndCareerEvent {
    data object InitClassList : CharacterClassAndCareerEvent()
    data class InitCareerList(val speciesName: String) : CharacterClassAndCareerEvent()

    data class OnClassSelect(val id: String) : CharacterClassAndCareerEvent()
    data class OnCareerSelect(val id: String) : CharacterClassAndCareerEvent()

    data object OnNextClick : CharacterClassAndCareerEvent()
}
