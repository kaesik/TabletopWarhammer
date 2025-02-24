package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.TalentDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.TalentItem

fun TalentDto.toTalentItem(): TalentItem {
    return TalentItem(
        id = id,
        name = name,
        max = max,
        tests = tests,
        description = description,
        source = source,
        page = page
    )
}
