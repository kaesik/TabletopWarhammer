package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MenuViewModel : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    fun onEvent(event: MenuEvent) {
    }
}
