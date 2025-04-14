package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

sealed class CharacterDetailsEvent {
    data object OnNextClick : CharacterDetailsEvent()
}
