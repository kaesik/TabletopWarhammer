package com.kaesik.tabletopwarhammer.main.presentation.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.remote.SupabaseLibrarySyncManager
import com.kaesik.tabletopwarhammer.main.domain.menu.MenuClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MenuViewModel(
    private val client: MenuClient,
    private val librarySyncManager: SupabaseLibrarySyncManager
) : ViewModel() {
    private val _state = MutableStateFlow(MenuState())
    val state = _state.asStateFlow()

    init {
        onEvent(MenuEvent.SyncLibraryData)
    }

    fun onEvent(event: MenuEvent) {
        when (event) {
            is MenuEvent.SyncLibraryData -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(isLoading = true)
                    librarySyncManager.syncAll()
                    _state.value = _state.value.copy(isLoading = false)
                }
            }

            else -> Unit
        }
    }
}
