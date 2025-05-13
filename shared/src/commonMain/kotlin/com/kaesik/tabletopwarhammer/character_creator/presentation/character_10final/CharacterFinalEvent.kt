package com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final

sealed class CharacterFinalEvent {
    data object OnSaveClick : CharacterFinalEvent()
}
