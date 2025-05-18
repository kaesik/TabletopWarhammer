package com.kaesik.tabletopwarhammer.auth.domain.di

interface PatternValidator {
    fun matches(email: String): Boolean
}
