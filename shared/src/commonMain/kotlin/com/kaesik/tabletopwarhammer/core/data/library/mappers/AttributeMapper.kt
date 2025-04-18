package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.AttributeDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem

fun AttributeDto.toAttributeItem(): AttributeItem {
    return AttributeItem(
        id = this.id,
        name = this.name,
        description = this.description,
        shortName = this.shortName,
        page = this.page,
    )
}
