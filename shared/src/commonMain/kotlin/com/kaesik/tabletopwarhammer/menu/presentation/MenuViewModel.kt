package com.kaesik.tabletopwarhammer.menu.presentation

import kotlinx.coroutines.flow.MutableStateFlow

class MenuViewModel {
    private val _state = MutableStateFlow(MenuState())
    val state = _state

    fun onEvent(event: MenuEvent) {
    }
}
