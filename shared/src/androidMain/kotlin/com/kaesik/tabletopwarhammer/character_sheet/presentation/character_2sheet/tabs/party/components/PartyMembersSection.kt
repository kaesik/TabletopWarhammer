package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetEmptyRow
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionCard
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.CharacterSheetSectionHeader
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun PartyMembersSection(
    members: List<List<String>>,
    onMembersChange: (List<List<String>>) -> Unit
) {
    CharacterSheetSectionCard(
        header = { CharacterSheetSectionHeader(text = "Party Members") }
    ) {
        Column {
            if (members.isEmpty()) {
                CharacterSheetEmptyRow(text = "No party members")
            } else {
                members.forEachIndexed { index, member ->
                    val name = member.getOrNull(0).orEmpty()
                    val notes = member.getOrNull(1).orEmpty()

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black1)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        PartyLabeledFieldRow(
                            label = "Member",
                            value = name,
                            onValueChange = { newName ->
                                val updated = members.toMutableList()
                                while (updated.size <= index) {
                                    updated.add(listOf("", ""))
                                }
                                val current = updated[index].toMutableList()
                                while (current.size < 2) current.add("")
                                current[0] = newName
                                current[1] = notes
                                updated[index] = current
                                onMembersChange(updated)
                            },
                            singleLine = true
                        )
                        PartyLabeledFieldRow(
                            label = "Description",
                            value = notes,
                            onValueChange = { newNotes ->
                                val updated = members.toMutableList()
                                while (updated.size <= index) {
                                    updated.add(listOf("", ""))
                                }
                                val current = updated[index].toMutableList()
                                while (current.size < 2) current.add("")
                                current[0] = name
                                current[1] = newNotes
                                updated[index] = current
                                onMembersChange(updated)
                            },
                            singleLine = false
                        )
                    }

                    if (index != members.lastIndex) {
                        HorizontalDivider(thickness = 1.dp, color = Brown1)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(32.dp)
                    .background(Brown1)
                    .clickable {
                        val updated = members.toMutableList()
                        updated.add(listOf("New member", ""))
                        onMembersChange(updated)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Add member",
                    color = Black1,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
