package com.kaesik.tabletopwarhammer.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.AuthManager
import com.kaesik.tabletopwarhammer.auth.domain.di.UserDataValidator
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import com.kaesik.tabletopwarhammer.core.domain.util.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val client: AuthClient,
    private val authManager: AuthManager,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Init -> validateForm()
            is LoginEvent.OnTogglePasswordVisibility -> toggleVisibility()
            is LoginEvent.OnEmailChange -> updateEmail(event.email)
            is LoginEvent.OnPasswordChange -> updatePassword(event.password)
            is LoginEvent.OnLoginClick -> login()
            is LoginEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }

            is LoginEvent.LoginWithGoogle -> loginWithGoogle()
            else -> Unit
        }
    }

    private fun toggleVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun updateEmail(email: String) {
        _state.update { current ->
            val isValidEmail = userDataValidator.isValidEmail(email.trim())
            current.copy(
                email = email,
                isEmailValid = isValidEmail,
                canLogin = validateLogin(isValidEmail, current.password)
            )
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            current.copy(
                password = password,
                canLogin = validateLogin(current.isEmailValid, password)
            )
        }
    }

    private fun validateForm() {
        val current = _state.value
        _state.update {
            it.copy(
                canLogin = validateLogin(current.isEmailValid, current.password)
            )
        }
    }

    private fun validateLogin(isEmailValid: Boolean, password: String): Boolean {
        return isEmailValid && password.isNotBlank() && !_state.value.isLoggingIn
    }

    private fun login() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggingIn = true) }

            val result = client.login(
                email = _state.value.email.trim(),
                password = _state.value.password
            )

            _state.update { it.copy(isLoggingIn = false) }

            when (result) {
                is Resource.Error -> {
                    println("Login Error: ${result.error}")
                    val message = when (result.error) {
                        DataError.Network.UNAUTHORIZED -> "Email or password is incorrect."
                        DataError.Network.NO_INTERNET -> "No internet connection."
                        DataError.Network.REQUEST_TIMEOUT -> "Request timed out."
                        DataError.Network.SERVER_ERROR -> "Server error occurred."
                        DataError.Network.CONFLICT -> "Account already exists."
                        DataError.Network.EMAIL_NOT_CONFIRMED -> "Please confirm your email before logging in."
                        else -> "Unknown error occurred. Please check logs."
                    }
                    _state.update { it.copy(error = message) }
                }

                is Resource.Success -> {
                    SessionManager.isLoggedIn = true
                    _state.update { it.copy(error = null, isLoggedIn = true) }
                }
            }
        }
    }

    private fun loginWithGoogle() {
        viewModelScope.launch {
            _state.update { it.copy(isLoggingIn = true) }

            authManager.loginGoogleUser().collectLatest { result ->
                _state.update { it.copy(isLoggingIn = false) }

                when (result) {
                    is Resource.Success -> {
                        SessionManager.isLoggedIn = true
                        _state.update { it.copy(error = null, isLoggedIn = true) }
                    }

                    is Resource.Error -> {
                        val message = when (result.error) {
                            DataError.Network.NO_INTERNET -> "No internet connection. Please check your connection."
                            DataError.Network.REQUEST_TIMEOUT -> "Request timed out. Try again."
                            DataError.Network.UNAUTHORIZED -> "Google authentication failed. Please try again."
                            DataError.Network.SERVER_ERROR -> "Server error occurred. Please try again later."
                            else -> "An unexpected error occurred. Please try again."
                        }
                        _state.update { it.copy(error = message) }
                    }
                }
            }
        }
    }
}
