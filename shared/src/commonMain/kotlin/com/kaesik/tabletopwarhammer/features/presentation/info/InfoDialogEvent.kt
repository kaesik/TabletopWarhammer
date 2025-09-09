package com.kaesik.tabletopwarhammer.features.presentation.info

import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef

sealed class InfoDialogEvent {
    data class Open(val ref: InspectRef) : InfoDialogEvent()
    data object Close : InfoDialogEvent()
    data object Retry : InfoDialogEvent()
}
