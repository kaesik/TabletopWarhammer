package com.kaesik.tabletopwarhammer.auth.presentation.register

import com.kaesik.tabletopwarhammer.auth.domain.di.PasswordValidationState
import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class RegisterState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val email: String = "",
    val password: String = "",

    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val passwordValidationState: PasswordValidationState = PasswordValidationState(),
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
)
