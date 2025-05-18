package com.kaesik.tabletopwarhammer.auth.presentation.register

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.UserDataValidator
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import com.kaesik.tabletopwarhammer.core.domain.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val client: AuthClient,
    private val userDataValidator: UserDataValidator,
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Init -> validateForm()
            is RegisterEvent.OnTogglePasswordVisibilityClick -> togglePasswordVisibility()
            is RegisterEvent.OnEmailChange -> updateEmail(event.email)
            is RegisterEvent.OnPasswordChange -> updatePassword(event.password)
            is RegisterEvent.OnRegisterClick -> register()
            is RegisterEvent.ClearError -> {
                _state.update { it.copy(error = null) }
            }

            else -> Unit
        }
    }

    private fun togglePasswordVisibility() {
        _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    private fun updateEmail(email: String) {
        _state.update { current ->
            val isValidEmail = userDataValidator.isValidEmail(email.trim())
            current.copy(
                email = email,
                isEmailValid = isValidEmail,
                canRegister = validateRegister(isValidEmail, current.password)
            )
        }
    }

    private fun updatePassword(password: String) {
        _state.update { current ->
            val passwordValidation = userDataValidator.validatePassword(password)
            current.copy(
                password = password,
                passwordValidationState = passwordValidation,
                canRegister = validateRegister(current.isEmailValid, password)
            )
        }
    }

    private fun validateForm() {
        val current = _state.value
        _state.update {
            it.copy(
                canRegister = validateRegister(current.isEmailValid, current.password)
            )
        }
    }

    private fun validateRegister(isEmailValid: Boolean, password: String): Boolean {
        return isEmailValid &&
                userDataValidator.validatePassword(password).isValidPassword &&
                !_state.value.isRegistering
    }

    private fun register() {
        viewModelScope.launch {
            _state.update { it.copy(isRegistering = true) }

            val result = client.register(
                email = _state.value.email.trim(),
                password = _state.value.password
            )

            _state.update { it.copy(isRegistering = false) }

            when (result) {
                is Resource.Error -> {
                    val message = when (result.error) {
                        DataError.Network.CONFLICT -> "Email already exists."
                        DataError.Network.NO_INTERNET -> "No internet connection."
                        else -> "Unknown error occurred."
                    }
                    _state.update { it.copy(error = message) }
                }

                is Resource.Success -> {
                    _state.update { it.copy(error = null) }
                }
            }
        }
    }
}
