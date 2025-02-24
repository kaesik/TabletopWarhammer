package com.kaesik.tabletopwarhammer.library.domain.library.items

data class ItemItem(
    val id: String,
    val name: String,
    val group: String,
    val source: String,
    val ap: String,
    val availability: String,
    val carries: String,
    val damage: String,
    val description: String,
    val encumbrance: String,
    val isTwoHanded: Boolean,
    val locations: String,
    val penalty: String,
    val price: String,
    val qualitiesAndFlaws: String,
    val quantity: String,
    val range: String,
    val meleeRanged: String,
    val type: String,
    val page: Double
)
