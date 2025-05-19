package com.kaesik.tabletopwarhammer.auth.presentation.login

sealed class LoginEvent {
    data object Init : LoginEvent()
    data object ClearError : LoginEvent()
    data object OnTogglePasswordVisibility : LoginEvent()
    data object OnLoginClick : LoginEvent()
    data object OnGuestClick : LoginEvent()
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
    data object LoginWithGoogle : LoginEvent()
}
