package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.ItemDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.ItemItem

fun ItemDto.toItemItem(): ItemItem {
    return ItemItem(
        id = id,
        name = name,
        group = group,
        source = source,
        ap = ap,
        availability = availability,
        carries = carries,
        damage = damage,
        description = description,
        encumbrance = encumbrance,
        isTwoHanded = isTwoHanded,
        locations = locations,
        penalty = penalty,
        price = price,
        qualitiesAndFlaws = qualitiesAndFlaws,
        quantity = quantity,
        range = range,
        meeleRanged = meeleRanged,
        type = type,
        page = page
    )
}
