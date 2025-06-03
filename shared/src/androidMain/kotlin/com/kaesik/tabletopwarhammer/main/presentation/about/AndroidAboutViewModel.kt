package com.kaesik.tabletopwarhammer.main.presentation.about

import androidx.lifecycle.ViewModel

class AndroidAboutViewModel : ViewModel() {

    private val viewModel by lazy {
        AboutViewModel()
    }

    val state = viewModel.state

    suspend fun onEvent(event: AboutEvent) {
        viewModel.onEvent(event)
    }
}
