package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.party

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.tabs.general.GeneralTextField
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterItem
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun CharacterSheetPartyTab(
    character: CharacterItem,
    onCharacterChange: (CharacterItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    PartyHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black1)
                            .padding(8.dp)
                    ) {
                        GeneralTextField(
                            value = character.partyName,
                            onValueChange = { newName ->
                                onCharacterChange(character.copy(partyName = newName))
                            },
                            singleLine = true
                        )
                    }
                }
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    PartyMembersHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    val members = character.partyMembers
                    if (members.isEmpty()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(32.dp)
                                .background(Black1),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "No party members",
                                modifier = Modifier
                                    .padding(horizontal = 8.dp),
                                color = Brown1
                            )
                        }
                    } else {
                        members.forEachIndexed { index, member ->
                            PartyMemberRow(
                                name = member.getOrNull(0).orEmpty(),
                                notes = member.getOrNull(1).orEmpty(),
                                onMemberChange = { newName, newNotes ->
                                    val updated = character.partyMembers.toMutableList()
                                    while (updated.size <= index) {
                                        updated.add(listOf("", ""))
                                    }
                                    val current = updated[index].toMutableList()
                                    while (current.size < 2) current.add("")
                                    current[0] = newName
                                    current[1] = newNotes
                                    updated[index] = current
                                    onCharacterChange(character.copy(partyMembers = updated))
                                }
                            )
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
                                val updated = character.partyMembers.toMutableList()
                                updated.add(listOf("New member", ""))
                                onCharacterChange(character.copy(partyMembers = updated))
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


        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Brown1)
            ) {
                Column {
                    PartyAmbitionsHeader()

                    HorizontalDivider(thickness = 1.dp, color = Brown1)

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Black1)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GeneralTextField(
                            label = "Short Term Ambition",
                            value = character.partyAmbitionShortTerm,
                            onValueChange = { onCharacterChange(
                                character.copy(partyAmbitionShortTerm = it)
                            ) }
                        )

                        GeneralTextField(
                            label = "Long Term Ambition",
                            value = character.partyAmbitionLongTerm,
                            onValueChange = { onCharacterChange(
                                character.copy(partyAmbitionLongTerm = it)
                            ) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PartyHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Party Name",
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PartyMembersHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Party Members",
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PartyAmbitionsHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(32.dp)
            .background(Black1),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Party Ambitions",
            modifier = Modifier
                .padding(horizontal = 8.dp),
            color = Brown1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun PartyMemberRow(
    name: String,
    notes: String,
    onMemberChange: (String, String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Black1)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Member",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                color = Brown1
            )
            Box(
                modifier = Modifier.weight(3f)
            ) {
                GeneralTextField(
                    label = "",
                    value = name,
                    onValueChange = { newName ->
                        onMemberChange(newName, notes)
                    },
                    singleLine = true
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Description",
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                color = Brown1
            )
            Box(
                modifier = Modifier.weight(3f)
            ) {
                GeneralTextField(
                    label = "",
                    value = notes,
                    onValueChange = { newNotes ->
                        onMemberChange(name, newNotes)
                    },
                    singleLine = false
                )
            }
        }
    }
}

@Composable
private fun VerticalGridDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Brown1)
    )
}

@Preview
@Composable
fun CharacterSheetPartyTabPreview() {
    val character = CharacterItem.default().copy(
        partyName = "The Brave Companions",
        partyMembers = listOf(
            listOf("Aric", "Leader of the group"),
            listOf("Borin", "Skilled warrior"),
            listOf("Lina", "Healer and mage")
        )
    )

    CharacterSheetPartyTab(
        character = character,
        onCharacterChange = {}
    )
}
