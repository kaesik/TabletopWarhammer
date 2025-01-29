package com.kaesik.tabletopwarhammer.library.domain.library.career

import com.kaesik.tabletopwarhammer.core.domain.util.CommonFlow

interface CareerDataSource {
    suspend fun getCareers(): CommonFlow<List<CareerItem>>
}
