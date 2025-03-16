package com.kaesik.tabletopwarhammer.library.domain.library.items

data class ItemItem(
    override val id: String,
    override val name: String,
    val group: String?,
    val source: String?,
    val ap: String?,
    val availability: String?,
    val carries: String?,
    val damage: String?,
    val description: String?,
    val encumbrance: String?,
    val isTwoHanded: Boolean?,
    val locations: String?,
    val penalty: String?,
    val price: String?,
    val qualitiesAndFlaws: String?,
    val quantity: String?,
    val range: String?,
    val meeleRanged: String?,
    val type: String?,
    val page: Int?,
): LibraryItem
