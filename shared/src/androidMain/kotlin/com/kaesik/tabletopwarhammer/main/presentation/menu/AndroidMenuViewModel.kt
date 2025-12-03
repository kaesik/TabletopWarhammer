package com.kaesik.tabletopwarhammer.main.presentation.menu

import androidx.lifecycle.ViewModel
import com.kaesik.tabletopwarhammer.core.domain.remote.LibraryStartupSync
import com.kaesik.tabletopwarhammer.main.domain.menu.MenuClient

class AndroidMenuViewModel(
    private val client: MenuClient,
    private val libraryStartupSync: LibraryStartupSync
) : ViewModel() {

    private val viewModel by lazy {
        MenuViewModel(
            client = client,
            libraryStartupSync = libraryStartupSync
        )
    }

    val state = viewModel.state

    fun onEvent(event: MenuEvent) {
        viewModel.onEvent(event)
    }
}
