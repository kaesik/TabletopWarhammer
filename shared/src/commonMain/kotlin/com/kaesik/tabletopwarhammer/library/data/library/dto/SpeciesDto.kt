package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SpeciesDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("opinions")
    val opinions: String,
    @SerialName("source")
    val source: String,
    @SerialName("weapon_skill")
    val weaponSkill: String,
    @SerialName("ballistic_skill")
    val ballisticSkill: String,
    @SerialName("strength")
    val strength: String,
    @SerialName("toughness")
    val toughness: String,
    @SerialName("agility")
    val agility: String,
    @SerialName("dexterity")
    val dexterity: String,
    @SerialName("intelligence")
    val intelligence: String,
    @SerialName("willpower")
    val willpower: String,
    @SerialName("fellowship")
    val fellowship: String,
    @SerialName("wounds")
    val wounds: String,
    @SerialName("fate_points")
    val fatePoints: String,
    @SerialName("resilience")
    val resilience: String,
    @SerialName("extra_points")
    val extraPoints: String,
    @SerialName("movement")
    val movement: String,
    @SerialName("skills")
    val skills: String,
    @SerialName("talents")
    val talents: String,
    @SerialName("forenames")
    val forenames: String,
    @SerialName("surnames")
    val surnames: String,
    @SerialName("clans")
    val clans: String,
    @SerialName("epithets")
    val epithets: String,
    @SerialName("age")
    val age: String,
    @SerialName("eye_colour")
    val eyeColour: String,
    @SerialName("hair_colour")
    val hairColour: String,
    @SerialName("height")
    val height: String,
    @SerialName("initiative")
    val initiative: String,
    @SerialName("page")
    val page: Int,
    @SerialName("names")
    val names: String
)
