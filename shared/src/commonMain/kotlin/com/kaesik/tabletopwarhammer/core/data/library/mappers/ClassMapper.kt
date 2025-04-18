package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.ClassItem

fun ClassDto.toClassItem(): ClassItem {
    return ClassItem(
        id = id,
        name = name,
        description = description,
        trappings = trappings,
        careers = careers,
        page = page
    )
}
