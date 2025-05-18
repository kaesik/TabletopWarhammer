package com.kaesik.tabletopwarhammer.auth.data.di

import com.kaesik.tabletopwarhammer.auth.domain.di.PatternValidator

object EmailPatternValidator : PatternValidator {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")

    override fun matches(email: String): Boolean {
        return emailRegex.matches(email.trim())
    }
}
