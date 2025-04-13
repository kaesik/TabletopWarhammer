package com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career

sealed class CharacterClassAndCareerEvent {
    data object InitClassList : CharacterClassAndCareerEvent()
    data object InitCareerList : CharacterClassAndCareerEvent()

    data object OnClassSelect : CharacterClassAndCareerEvent()
    data object OnCareerSelect : CharacterClassAndCareerEvent()

    data object OnNextClick : CharacterClassAndCareerEvent()
}
