package com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings

sealed class CharacterTrappingsEvent {
    data class InitTrappingsList(
        val className: String,
        val careerPathName: String,
    ) : CharacterTrappingsEvent()

    data class InitWealth(val careerPathName: String) : CharacterTrappingsEvent()

    data class SetWealthCached(val wealth: List<Int>) : CharacterTrappingsEvent()

    data object OnNextClick : CharacterTrappingsEvent()
}
