package com.kaesik.tabletopwarhammer.menu.domain

import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.EmptyResult

interface MenuClient {
    suspend fun logout(): EmptyResult<DataError.Network>
}
