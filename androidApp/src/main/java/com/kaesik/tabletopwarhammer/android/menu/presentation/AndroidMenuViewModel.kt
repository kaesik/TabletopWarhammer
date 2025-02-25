package com.kaesik.tabletopwarhammer.android.menu.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.MenuEvent
import com.kaesik.tabletopwarhammer.menu.presentation.MenuViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AndroidMenuViewModel @Inject constructor(

): ViewModel() {

    private val viewModel by lazy {
        MenuViewModel(
        )
    }

    val state = viewModel.state

    fun onEvent(event: MenuEvent) {
        viewModel.onEvent(event)
    }
}
