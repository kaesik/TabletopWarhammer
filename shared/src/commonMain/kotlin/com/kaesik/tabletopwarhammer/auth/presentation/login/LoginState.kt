package com.kaesik.tabletopwarhammer.auth.presentation.login

data class LoginState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isLoggingIn: Boolean = false,
    val isLoggedIn: Boolean = false,
    val canLogin: Boolean = false,
)
