package com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.talents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.SourceLabelBadge
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getBaseName
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.getFullNameWithSpecialization
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.components.normalizeSkillOrTalentName
import com.kaesik.tabletopwarhammer.core.data.library.LibraryEnum
import com.kaesik.tabletopwarhammer.core.domain.info.InspectRef
import com.kaesik.tabletopwarhammer.core.domain.library.items.TalentItem
import com.kaesik.tabletopwarhammer.features.info.InspectInfoIcon
import com.kaesik.tabletopwarhammer.features.info.LocalOpenInfo

@Composable
fun TalentTableRowSpecialization(
    talent: TalentItem,
    isSelected: Boolean,
    sourceLabel: String,
    options: List<String>,
    loading: Boolean,
    onRequestOptions: () -> Unit,
    onResolved: (TalentItem) -> Unit,
) {
    val openInfo = LocalOpenInfo.current

    var showDialog by remember { mutableStateOf(false) }
    var picked by remember { mutableStateOf<String?>(null) }
    var custom by remember { mutableStateOf("") }

    val base = remember(talent.name) { getBaseName(talent.name) }

    fun parseSpecFromName(name: String): String {
        val i = name.indexOf('(')
        return if (i >= 0) {
            name.substring(i + 1).removeSuffix(")").trim().ifBlank { "Any" }
        } else {
            "Any"
        }
    }

    var shownSpec by remember(talent.name) {
        mutableStateOf(parseSpecFromName(talent.name))
    }

    val allowCustom = options.any { it.equals("Other", true) }
    val effectivePicked = when {
        picked == null -> null
        picked!!.equals("Other", true) && allowCustom -> custom.ifBlank { null }
        else -> picked
    }

    Surface(shadowElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showDialog = true
                    onRequestOptions()
                }
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(Modifier.weight(2f)) {
                Text(
                    text = normalizeSkillOrTalentName(
                        getFullNameWithSpecialization(base, shownSpec)
                    )
                )
            }
            InspectInfoIcon(onClick = {
                openInfo(InspectRef(type = LibraryEnum.TALENT, key = base))
            })
            Row(verticalAlignment = Alignment.CenterVertically) {
                SourceLabelBadge(sourceLabel)
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(base) },
            text = {
                if (loading) {
                    Box(
                        Modifier
                            .height(120.dp)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) { CircularProgressIndicator() }
                } else {
                    Column {
                        if (options.isEmpty()) {
                            Text("None")
                        } else {
                            Column(
                                Modifier
                                    .heightIn(max = 260.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                options.forEach { opt ->
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                picked = opt
                                                shownSpec =
                                                    if (opt.equals("Other", true) && allowCustom) {
                                                        shownSpec
                                                    } else {
                                                        opt
                                                    }
                                            }
                                            .padding(vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val marker = when {
                                            picked == opt -> "  ✓"
                                            else -> ""
                                        }
                                        Text(opt + marker)
                                    }
                                }
                            }
                        }
                        if (allowCustom && picked?.equals("Other", true) == true) {
                            OutlinedTextField(
                                value = custom,
                                onValueChange = {
                                    custom = it
                                    shownSpec = if (it.isBlank()) shownSpec else it
                                },
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
                    onClick = {
                        val spec = effectivePicked!!
                        shownSpec = spec
                        onResolved(talent.copy(name = getFullNameWithSpecialization(base, spec)))
                        showDialog = false
                    }
                ) { Text("OK") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancel") } }
        )
    }
}
