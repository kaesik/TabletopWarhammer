package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.components.ClassOrCareer

sealed class CharacterTrappingsEvent {
    data class InitTrappingsList(val from: ClassOrCareer) : CharacterTrappingsEvent()

    data object OnNextClick : CharacterTrappingsEvent()
}
