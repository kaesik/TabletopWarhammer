package com.kaesik.tabletopwarhammer.auth.presentation.login

import com.kaesik.tabletopwarhammer.core.domain.util.DataError

data class LoginState(
    val error: DataError? = null,
    val message: String? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false,

    val email: String = "",
    val password: String = "",

    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val canLogin: Boolean = false,
)
