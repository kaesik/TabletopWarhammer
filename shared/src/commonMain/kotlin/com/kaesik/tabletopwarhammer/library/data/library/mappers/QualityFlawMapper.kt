package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.QualityFlawDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.QualityFlawItem

fun QualityFlawDto.toQualityFlawItem(): QualityFlawItem {
    return QualityFlawItem(
        id = id,
        name = name,
        group = group,
        description = description,
        isQuality = isQuality,
        source = source,
        page = page
    )
}
