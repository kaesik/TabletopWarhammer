package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CharacterSheetStepperDialog(
    title: String,
    initialValue: Int,
    onConfirm: (Int) -> Unit,
    onDismiss: () -> Unit
) {
    val tempValue = remember { mutableIntStateOf(initialValue.coerceAtLeast(0)) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
        text = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CharacterSheetStepper(
                    value = tempValue.intValue,
                    onIncrement = { tempValue.intValue += 1 },
                    onDecrement = {
                        tempValue.intValue = (tempValue.intValue - 1).coerceAtLeast(0)
                    },
                    modifier = Modifier
                        .height(32.dp)
                        .weight(1f)
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(tempValue.intValue) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
