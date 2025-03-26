package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.lifecycle.ViewModel

class AndroidMenuViewModel : ViewModel() {

    private val viewModel by lazy {
        MenuViewModel(
        )
    }

    val state = viewModel.state

    fun onEvent(event: MenuEvent) {
        viewModel.onEvent(event)
    }
}
