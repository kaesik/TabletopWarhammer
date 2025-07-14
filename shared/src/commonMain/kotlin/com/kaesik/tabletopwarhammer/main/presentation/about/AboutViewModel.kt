package com.kaesik.tabletopwarhammer.main.presentation.about

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AboutViewModel : ViewModel() {
    private val _state = MutableStateFlow(AboutState())
    val state = _state.asStateFlow()

    suspend fun onEvent(event: AboutEvent) {
        when (event) {

            else -> Unit
        }
    }
}
