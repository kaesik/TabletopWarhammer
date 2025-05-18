package com.kaesik.tabletopwarhammer.auth.presentation.intro

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class IntroViewModel : ViewModel() {

    private val _state = MutableStateFlow(IntroState())
    val state = _state.asStateFlow()

    fun onEvent(event: IntroEvent) {
        when (event) {
            IntroEvent.OnSignInClick -> {

            }

            IntroEvent.OnSignUpClick -> {

            }

            IntroEvent.OnGuestClick -> {

            }

            else -> Unit
        }
    }
}
