package com.kaesik.tabletopwarhammer.core.data.library.mappers

import com.kaesik.tabletopwarhammer.core.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.core.domain.library.items.CareerItem

fun CareerDto.toCareerItem(): CareerItem {
    return CareerItem(
        id = id,
        name = name,
        limitations = limitations,
        description = description,
        advanceScheme = advanceScheme,
        quotations = quotations,
        adventuring = adventuring,
        source = source,
        careerPath = careerPath,
        className = className,
        page = page
    )
}
