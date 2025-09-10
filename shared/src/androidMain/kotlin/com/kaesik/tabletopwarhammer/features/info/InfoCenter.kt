package com.kaesik.tabletopwarhammer.features.info

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.features.presentation.info.InfoDialogEvent
import org.koin.androidx.compose.koinViewModel

val LocalOpenInfo = staticCompositionLocalOf { { _: InspectRef -> } }

@Composable
fun ProvideInfoCenter(content: @Composable () -> Unit) {
    val vm: AndroidInfoDialogViewModel = koinViewModel()
    val openInfo = remember(vm) { { ref: InspectRef -> vm.onEvent(InfoDialogEvent.Open(ref)) } }
    CompositionLocalProvider(LocalOpenInfo provides openInfo) {
        content()
        InfoDialog(viewModel = vm)
    }
}
