package com.kaesik.tabletopwarhammer.core.domain.library.items

import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem

data class ItemItem(
    override val id: String,
    override val name: String,
    val group: String? = null,
    val source: String? = null,
    val ap: String? = null,
    val availability: String? = null,
    val carries: String? = null,
    val damage: String? = null,
    val description: String? = null,
    val encumbrance: String? = null,
    val isTwoHanded: Boolean? = null,
    val locations: String? = null,
    val penalty: String? = null,
    val price: String? = null,
    val qualitiesAndFlaws: String? = null,
    val quantity: String? = null,
    val range: String? = null,
    val meeleRanged: String? = null,
    val type: String? = null,
    val page: Int? = null,
) : LibraryItem
