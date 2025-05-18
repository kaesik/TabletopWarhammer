package com.kaesik.tabletopwarhammer.menu.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.menu.domain.MenuClient

class AndroidMenuViewModel(
    private val client: MenuClient
) : ViewModel() {

    private val viewModel by lazy {
        MenuViewModel(
            client = client,
        )
    }

    val state = viewModel.state

    suspend fun onEvent(event: MenuEvent) {
        viewModel.onEvent(event)
    }
}
