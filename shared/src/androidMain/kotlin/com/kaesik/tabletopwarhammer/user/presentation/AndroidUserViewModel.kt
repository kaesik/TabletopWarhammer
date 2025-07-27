package com.kaesik.tabletopwarhammer.user.presentation

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.user.domain.UserClient

class AndroidUserViewModel(
    private val userClient: UserClient
): ViewModel() {

    private val viewModel by lazy {
        UserViewModel(
            userClient = userClient,
        )
    }

    val state = viewModel.state

    fun onEvent(event: UserEvent) {
        viewModel.onEvent(event)
    }
}
