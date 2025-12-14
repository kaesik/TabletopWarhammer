package com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun CharacterSheetNumberField(
    value: Int,
    onValueChange: (Int) -> Unit,
    label: String? = null,
    centerText: Boolean = true,
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Column {
        if (label != null) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Brown1
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Box(
            modifier = modifier
                .height(32.dp)
                .background(Brown1)
                .padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            BasicTextField(
                value = value.toString(),
                onValueChange = { text ->
                    val intValue = text.toIntOrNull() ?: 0
                    onValueChange(intValue)
                },
                textStyle = LocalTextStyle.current.copy(
                    color = Black1,
                    textAlign = if (centerText) TextAlign.Center else TextAlign.Start
                ),
                cursorBrush = SolidColor(Black1),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
