package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.ClassDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.ClassItem

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
