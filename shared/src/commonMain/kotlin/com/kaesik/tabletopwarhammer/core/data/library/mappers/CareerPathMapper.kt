package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerPathItem

fun CareerPathDto.toCareerPathItem(): CareerPathItem {
    return CareerPathItem(
        id = id,
        name = name,
        skills = skills,
        status = status,
        trappings = trappings,
        talents = talents
    )
}
