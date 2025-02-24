package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.CareerPathDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerPathItem

fun CareerPathDto.toCareerPathItem(): CareerPathItem {
    return CareerPathItem(
        id = id,
        name = name,
        limitations = limitations,
        summary = summary,
        description = description,
        advanceScheme = advanceScheme,
        quotations = quotations,
        adventuring = adventuring,
        source = source,
        careerPath = careerPath,
        className = className,
        page = page,
    )
}
