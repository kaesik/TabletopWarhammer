package com.kaesik.tabletopwarhammer.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kaesik.tabletopwarhammer.core.theme.Black1
import com.kaesik.tabletopwarhammer.core.theme.Black2
import com.kaesik.tabletopwarhammer.core.theme.Brown1
import com.kaesik.tabletopwarhammer.core.theme.Brown2

@Composable
fun WarhammerButton(
    text: String,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Brown1,
            contentColor = Black1,
            disabledContainerColor = Black2,
            disabledContentColor = Black1,
        ),
        shape = RoundedCornerShape(100f),
        border = BorderStroke(
            width = 3.dp,
            color = Black1
        ),
        modifier = modifier
            .height(38.dp)
            .width(256.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(15.dp)
                    .alpha(if (isLoading) 1f else 0f),
                strokeWidth = 1.5.dp,
                color = Black1
            )
            Text(
                text = text,
                modifier = Modifier
                    .alpha(if (isLoading) 0f else 1f),
                style = typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun WarhammerButtonPreview() {
    WarhammerButton(
        text = "Button",
        isLoading = false,
        onClick = {}
    )
}
