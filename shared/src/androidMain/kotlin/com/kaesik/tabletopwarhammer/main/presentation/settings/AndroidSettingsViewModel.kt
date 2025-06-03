package com.kaesik.tabletopwarhammer.main.presentation.settings

import androidx.lifecycle.ViewModel

class AndroidSettingsViewModel : ViewModel() {

    private val viewModel by lazy {
        SettingsViewModel()
    }

    val state = viewModel.state

    suspend fun onEvent(event: SettingsEvent) {
        viewModel.onEvent(event)
    }
}
