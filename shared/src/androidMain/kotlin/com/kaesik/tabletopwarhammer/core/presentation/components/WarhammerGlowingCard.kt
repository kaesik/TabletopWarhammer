package com.kaesik.tabletopwarhammer.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.components.WarhammerSectionTitle
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Brown2
import com.kaesik.tabletopwarhammer.core.theme.Light1

@Composable
fun WarhammerGlowingCard(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    cornerRadius: Dp = 32.dp,
    glowSize: Dp = 32.dp,
    glowColor: Color = Light1,
    onClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)

    Box(modifier = modifier.wrapContentHeight()) {

        Card(
            onClick = onClick,
            enabled = !isLoading,
            shape = shape,
            colors = CardDefaults.cardColors(
                containerColor = Black1,
                contentColor = Brown2
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = glowSize,
                    shape = shape,
                    ambientColor = glowColor,
                    spotColor = glowColor
                )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(18.dp),
                content = content
            )
        }

        if (isLoading) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Black1),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Brown2)
            }
        }
    }
}

@Preview
@Composable
fun WarhammerGlowingCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        WarhammerGlowingCard(
            isLoading = false,
            modifier = Modifier
                .fillMaxWidth(),
            content = {
                WarhammerSectionTitle(
                    "Title"
                )
                WarhammerButton(
                    text = "Button 1",
                    onClick = {},
                    isLoading = false,
                    enabled = true
                )
                WarhammerButton(
                    text = "Button 2",
                    onClick = {},
                    isLoading = false,
                    enabled = false
                )
                WarhammerButton(
                    text = "Button 3",
                    onClick = {},
                    isLoading = false,
                    enabled = false
                )
                WarhammerButton(
                    text = "Button 4",
                    onClick = {},
                    isLoading = false,
                    enabled = false
                )
            },
        )
    }
}
