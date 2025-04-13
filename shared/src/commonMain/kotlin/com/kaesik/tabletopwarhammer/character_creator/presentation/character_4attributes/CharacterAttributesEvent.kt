package com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes

sealed class CharacterAttributesEvent {
    data object InitAttributesList : CharacterAttributesEvent()

    data object OnNextClick : CharacterAttributesEvent()
}
