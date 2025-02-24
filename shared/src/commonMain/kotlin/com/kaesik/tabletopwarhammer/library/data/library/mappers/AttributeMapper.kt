package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem

fun AttributeDto.toAttributeItem(): AttributeItem {
    return AttributeItem(
        id = this.id,
        name = this.name,
        description = this.description,
        shortName = this.shortName,
        page= this.page,
    )
}
