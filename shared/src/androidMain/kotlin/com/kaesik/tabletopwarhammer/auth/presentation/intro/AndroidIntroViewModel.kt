package com.kaesik.tabletopwarhammer.auth.presentation.intro

import androidx.lifecycle.ViewModel

class AndroidIntroViewModel : ViewModel() {
    private val viewModel by lazy {
        IntroViewModel()
    }

    val state = viewModel.state

    fun onEvent(event: IntroEvent) {
        viewModel.onEvent(event)
    }
}
