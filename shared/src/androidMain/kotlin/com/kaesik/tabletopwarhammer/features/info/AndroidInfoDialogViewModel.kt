package com.kaesik.tabletopwarhammer.features.info

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.info.InfoRepository
import com.kaesik.tabletopwarhammer.features.presentation.info.InfoDialogEvent
import com.kaesik.tabletopwarhammer.features.presentation.info.InfoDialogViewModel

class AndroidInfoDialogViewModel(
    private val repo: InfoRepository
) : ViewModel() {

    private val viewModel by lazy {
        InfoDialogViewModel(
            repo = repo
        )
    }

    val state = viewModel.state

    fun onEvent(event: InfoDialogEvent) {
        viewModel.onEvent(event)
    }
}
