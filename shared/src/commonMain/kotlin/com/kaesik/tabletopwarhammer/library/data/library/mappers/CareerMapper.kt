package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.CareerDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerItem

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
