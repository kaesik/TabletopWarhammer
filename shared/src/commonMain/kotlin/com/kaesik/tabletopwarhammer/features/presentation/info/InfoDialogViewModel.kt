package com.kaesik.tabletopwarhammer.features.presentation.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaesik.tabletopwarhammer.core.domain.info.InfoDetails
import com.kaesik.tabletopwarhammer.core.domain.info.InfoRepository
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.util.DataError
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InfoDialogViewModel(
    private val repo: InfoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(InfoDialogState())
    val state = _state.asStateFlow()

    private val cache = mutableMapOf<InspectRef, InfoDetails>()
    private var job: Job? = null

    fun onEvent(event: InfoDialogEvent) {
        when (event) {
            is InfoDialogEvent.Open -> open(event.ref)
            InfoDialogEvent.Close -> _state.value = InfoDialogState()
            InfoDialogEvent.Retry -> _state.value.currentRef?.let { open(it, force = true) }
        }
    }

    private fun open(ref: InspectRef, force: Boolean = false) {
        cache[ref]?.takeIf { !force }?.let {
            _state.value = InfoDialogState(isOpen = true, details = it, currentRef = ref)
            return
        }
        job?.cancel()
        _state.value = InfoDialogState(isOpen = true, isLoading = true, currentRef = ref)

        job = viewModelScope.launch {
            runCatching { repo.getDetails(ref) }
                .onSuccess { details ->
                    if (details == null) {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                error = DataError.Local.UNKNOWN
                            )
                        }
                    } else {
                        cache[ref] = details

                        _state.update {
                            it.copy(
                                isLoading = false,
                                details = details
                            )
                        }
                    }
                }
                .onFailure {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = DataError.Local.UNKNOWN
                        )
                    }
                }
        }
    }
}
