package com.kaesik.tabletopwarhammer.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MainBottomBar(
    onHomeClick: () -> Unit = {},
    onLibraryClick: () -> Unit = {},
    onUserClick: () -> Unit = {},
) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onLibraryClick) {
                Icon(
                    Icons.Default.MenuBook,
                    modifier = Modifier.size(64.dp),
                    contentDescription = "Library"
                )
            }
            IconButton(onClick = onHomeClick) {
                Icon(
                    Icons.Default.Home,
                    modifier = Modifier.size(64.dp),
                    contentDescription = "Home")
            }
            IconButton(onClick = onUserClick) {
                Icon(
                    Icons.Default.Person,
                    modifier = Modifier.size(64.dp),
                    contentDescription = "User"
                )
            }
        }
    }
}

@Preview
@Composable
fun MainBottomBarPreview() {
    MainBottomBar()
}
