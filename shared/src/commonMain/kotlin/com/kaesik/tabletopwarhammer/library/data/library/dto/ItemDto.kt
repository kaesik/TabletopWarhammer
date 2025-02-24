package com.kaesik.tabletopwarhammer.library.data.library.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
    @SerialName("group")
    val group: String,
    @SerialName("source")
    val source: String,
    @SerialName("ap")
    val ap: String,
    @SerialName("availability")
    val availability: String,
    @SerialName("carries")
    val carries: String,
    @SerialName("damage")
    val damage: String,
    @SerialName("description")
    val description: String,
    @SerialName("encumbrance")
    val encumbrance: String,
    @SerialName("is_2h")
    val isTwoHanded: Boolean,
    @SerialName("locations")
    val locations: String,
    @SerialName("penalty")
    val penalty: String,
    @SerialName("price")
    val price: String,
    @SerialName("qualities_and_flaws")
    val qualitiesAndFlaws: String,
    @SerialName("quantity")
    val quantity: String,
    @SerialName("range")
    val range: String,
    @SerialName("meele_ranged")
    val meleeRanged: String,
    @SerialName("type")
    val type: String,
    @SerialName("page")
    val page: Double
)
