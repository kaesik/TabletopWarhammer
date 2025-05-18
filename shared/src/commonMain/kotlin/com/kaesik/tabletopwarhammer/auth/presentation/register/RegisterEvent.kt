package com.kaesik.tabletopwarhammer.auth.presentation.register

sealed class RegisterEvent {
    data object Init : RegisterEvent()
    data object ClearError : RegisterEvent()
    data object OnTogglePasswordVisibilityClick : RegisterEvent()
    data object OnLoginClick : RegisterEvent()
    data object OnRegisterClick : RegisterEvent()
    data class OnEmailChange(val email: String) : RegisterEvent()
    data class OnPasswordChange(val password: String) : RegisterEvent()
    data class ShowError(val message: String) : RegisterEvent()
    data object RegistrationSuccess : RegisterEvent()
}
