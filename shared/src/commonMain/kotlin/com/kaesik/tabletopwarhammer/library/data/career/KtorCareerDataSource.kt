package com.kaesik.tabletopwarhammer.library.data.career

interface KtorCareerDataSource {
    fun getCareers(): List<CareerDto>
}
