package com.kaesik.tabletopwarhammer.main.presentation.menu

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.main.domain.menu.MenuClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel(
    private val client: MenuClient,
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: MenuEvent) {
        when (event) {

            else -> Unit
        }
    }
}
