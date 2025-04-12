package com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator

sealed class CharacterCreatorEvent {
    data object OnCreateCharacterSelect : CharacterCreatorEvent()
    data object OnRandomCharacterSelect : CharacterCreatorEvent()
}
