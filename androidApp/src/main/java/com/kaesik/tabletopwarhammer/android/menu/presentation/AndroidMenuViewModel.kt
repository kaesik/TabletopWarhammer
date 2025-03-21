package com.kaesik.tabletopwarhammer.android.menu.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.MenuEvent
import com.kaesik.tabletopwarhammer.menu.presentation.MenuViewModel

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
