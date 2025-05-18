package com.kaesik.tabletopwarhammer.auth.domain

interface PatternValidator {
    fun matches(email: String): Boolean
}
