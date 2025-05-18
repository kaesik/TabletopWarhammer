package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.menu.domain.MenuClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel(
    private val client: MenuClient,
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.OnLogoutClick -> logout()

            else -> Unit
        }
    }

    private suspend fun logout() {
        client.logout()
        _state.value = _state.value.copy(isLoggedOut = true)
    }
}
