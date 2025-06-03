package com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details

sealed class CharacterDetailsEvent {
    data class OnNameChanged(val value: String) : CharacterDetailsEvent()
    data class OnForenameChanged(val value: String) : CharacterDetailsEvent()
    data class OnSurnameChanged(val value: String) : CharacterDetailsEvent()
    data class OnAgeChanged(val value: String) : CharacterDetailsEvent()
    data class OnHeightChanged(val value: String) : CharacterDetailsEvent()
    data class OnHairColorChanged(val value: String) : CharacterDetailsEvent()
    data class OnEyeColorChanged(val value: String) : CharacterDetailsEvent()
    data class OnClanChanged(val value: String) : CharacterDetailsEvent()
    data class OnEpithetChanged(val value: String) : CharacterDetailsEvent()

    data class GenerateRandomName(val species: String) : CharacterDetailsEvent()

    data class RollName(val species: String) : CharacterDetailsEvent()
    data class RollForename(val species: String) : CharacterDetailsEvent()
    data class RollSurname(val species: String) : CharacterDetailsEvent()
    data class RollClan(val species: String) : CharacterDetailsEvent()
    data class RollEpithet(val species: String) : CharacterDetailsEvent()

    data object OnNextClick : CharacterDetailsEvent()
}
