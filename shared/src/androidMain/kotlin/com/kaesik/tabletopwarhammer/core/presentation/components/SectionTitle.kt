package com.kaesik.tabletopwarhammer.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Brown1

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = typography.titleLarge,
        color = Brown1,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Preview
@Composable
fun SectionTitlePreview() {
    SectionTitle(title = "Section Title")
}
