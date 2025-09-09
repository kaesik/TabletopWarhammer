package com.kaesik.tabletopwarhammer.features.info

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.features.presentation.info.InfoDialogEvent
import io.ktor.websocket.Frame
import org.koin.androidx.compose.koinViewModel

@Composable
fun InfoDialog(viewModel: AndroidInfoDialogViewModel = koinViewModel()) {
    val state = viewModel.state.collectAsState().value
    if (!state.isOpen) return

    AlertDialog(
        onDismissRequest = { viewModel.onEvent(InfoDialogEvent.Close) },
        confirmButton = {
            TextButton(onClick = { viewModel.onEvent(InfoDialogEvent.Close) }) {
                Frame.Text(
                    "Close"
                )
            }
        },
        title = {
            Text(
                state.details?.title ?: "Details",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        },
        text = {
            when {
                state.isLoading -> Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }

                state.error != null -> Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextButton(onClick = { viewModel.onEvent(InfoDialogEvent.Retry) }) { Text("Try again") }
                }

                else -> {
                    val d = state.details!!
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 440.dp)
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        d.subtitle?.let { Text(it, style = MaterialTheme.typography.titleSmall) }
                        Text(d.description, style = MaterialTheme.typography.bodyMedium)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            d.source?.takeIf { it.isNotBlank() }?.let {
                                AssistChip(onClick = {}, label = { Text(it) })
                            }
                            d.page?.let { AssistChip(onClick = {}, label = { Text("p. $it") }) }
                        }
                    }
                }
            }
        }
    )
}
