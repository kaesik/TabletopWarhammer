package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpecializationDialog(
    base: String,
    loading: Boolean,
    options: List<String>,
    forbiddenSpecsLower: Set<String>,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var picked by remember(base, options) { mutableStateOf<String?>(null) }
    var custom by remember(base) { mutableStateOf("") }

    val filtered = remember(options, forbiddenSpecsLower) {
        options.filterNot { it.lowercase() in forbiddenSpecsLower }
    }
    val allowCustom = remember(options, filtered) {
        filtered.any { it.equals("Other", true) } || options.any { it.equals("Other", true) }
    }
    val effectivePicked = when {
        picked == null -> null
        picked!!.equals("Other", true) && allowCustom -> custom.ifBlank { null }
        else -> picked
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(base) },
        text = {
            if (loading) {
                Box(
                    Modifier
                        .height(120.dp)
                        .fillMaxWidth()
                ) { CircularProgressIndicator() }
            } else {
                Column {
                    val listToShow =
                        if (filtered.isEmpty() && allowCustom) listOf("Other") else filtered
                    if (listToShow.isEmpty()) {
                        Text("None")
                    } else {
                        Column(
                            Modifier
                                .heightIn(max = 260.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            listToShow.forEach { opt ->
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 6.dp)
                                ) {
                                    Text(
                                        opt + if (picked == opt) "  ✓" else "",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 8.dp)
                                            .clickable { picked = opt }
                                    )
                                }
                            }
                        }
                    }
                    if (allowCustom && picked?.equals("Other", true) == true) {
                        OutlinedTextField(
                            value = custom,
                            onValueChange = { custom = it },
                            label = { Text("Other") },
                            singleLine = true,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !loading && effectivePicked != null,
                onClick = { onConfirm(effectivePicked!!) }
            ) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}
