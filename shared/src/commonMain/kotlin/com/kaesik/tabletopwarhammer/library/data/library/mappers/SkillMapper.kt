package com.kaesik.tabletopwarhammer.library.data.library.mappers

import com.kaesik.tabletopwarhammer.library.data.library.dto.SkillDto
import com.kaesik.tabletopwarhammer.library.domain.library.items.SkillItem

fun SkillDto.toSkillItem(): SkillItem {
    return SkillItem(
        id = id,
        name = name,
        attribute = attribute,
        isBasic = isBasic,
        isGrouped = isGrouped,
        description = description,
        specialization = specialization,
        source = source,
        page = page
    )
}
