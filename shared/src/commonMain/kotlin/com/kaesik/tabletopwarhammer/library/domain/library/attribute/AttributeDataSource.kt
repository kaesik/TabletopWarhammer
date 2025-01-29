package com.kaesik.tabletopwarhammer.library.domain.library.attribute

import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow

interface AttributeDataSource {
    fun getAttributes(): CommonFlow<List<AttributeItem>>
}
