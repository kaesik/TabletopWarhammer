package com.kaesik.tabletopwarhammer.library.data.attribute

import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow
import com.kaesik.tabletopwarhammer.library.domain.library.attribute.AttributeItem
import kotlin.coroutines.CoroutineContext

interface KtorAttributeDataSource {
    fun getAttributes(context: CoroutineContext): CommonFlow<List<AttributeItem>>
}
