package com.kaesik.tabletopwarhammer.user.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.user.domain.UserClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job

class UserViewModel(
    private val userClient: UserClient
): ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state = _state.asStateFlow()

    private var userJob: Job? = null

    fun onEvent(event: UserEvent) {
        when (event) {

        }
    }
}
