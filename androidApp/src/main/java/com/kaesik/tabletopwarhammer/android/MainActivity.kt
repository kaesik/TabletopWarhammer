package com.kaesik.tabletopwarhammer.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kaesik.tabletopwarhammer.android.character_creator.presentation.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.android.character_creator.presentation.CharacterCreatorScreen
import com.kaesik.tabletopwarhammer.android.character_sheet.presentation.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.android.character_sheet.presentation.CharacterSheetScreen
import com.kaesik.tabletopwarhammer.android.core.presentation.Routes
import com.kaesik.tabletopwarhammer.android.library.presentation.library.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.android.library.presentation.library.LibraryScreen
import com.kaesik.tabletopwarhammer.android.library.presentation.library_item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.android.library.presentation.library_item.LibraryItemScreen
import com.kaesik.tabletopwarhammer.android.library.presentation.library_list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.android.library.presentation.library_list.LibraryListScreen
import com.kaesik.tabletopwarhammer.android.menu.presentation.AndroidMenuViewModel
import com.kaesik.tabletopwarhammer.android.menu.presentation.MenuScreen
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListEvent
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListState
import com.kaesik.tabletopwarhammer.menu.presentation.MenuEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainRoot()
                }
            }
        }
    }
}

@Composable
fun MainRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MENU) {
        composable(route = Routes.MENU) {
            val viewModel = hiltViewModel<AndroidMenuViewModel>()
            val state by viewModel.state.collectAsState()
            MenuScreen(
                state = state,
                onEvent = {event ->
                    when (event) {
                        is MenuEvent.NavigateToLibraryScreen -> {
                            navController.navigate(
                                Routes.LIBRARY
                            )
                        }
                        is MenuEvent.NavigateToCharacterSheetScreen -> {
                            navController.navigate(
                                Routes.CHARACTER_SHEET
                            )
                        }
                        is MenuEvent.NavigateToCharacterCreatorScreen -> {
                            navController.navigate(
                                Routes.CHARACTER_CREATOR
                            )
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(route = Routes.LIBRARY) {
            val viewModel = hiltViewModel<AndroidLibraryViewModel>()
            val state by viewModel.state.collectAsState()
            LibraryScreen(
                state = state,
                onEvent = {event ->
                    when (event) {
                        is LibraryEvent.LoadLibrary -> {
                            viewModel.onEvent(event)
                            navController.navigate(Routes.LIBRARY_LIST)
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(route = Routes.LIBRARY_LIST) {backStackEntry ->
            val viewModel = hiltViewModel<AndroidLibraryListViewModel>()
            val state by viewModel.state.collectAsState()

            LibraryListScreen(
                state = state,
                onEvent = { event ->
                    when (event) {
                        is LibraryListEvent.LoadItem -> {
                            viewModel.onEvent(event)
                            navController.navigate(Routes.LIBRARY_ITEM)
                        }
                        else -> viewModel.onEvent(event)
                    }
                }
            )
        }
        composable(route = Routes.LIBRARY_ITEM) {
            val viewModel = hiltViewModel<AndroidLibraryItemViewModel>()
            val state by viewModel.state.collectAsState()
            LibraryItemScreen(
                state = state,
                onEvent = {event ->
                    viewModel.onEvent(event)
                }
            )
        }
        composable(route = Routes.CHARACTER_SHEET) {
            val viewModel = hiltViewModel<AndroidCharacterSheetViewModel>()
            val state by viewModel.state.collectAsState()
            CharacterSheetScreen(
                state = state,
                onEvent = {event ->
                    viewModel.onEvent(event)
                }
            )
        }
        composable(route = Routes.CHARACTER_CREATOR) {
            val viewModel = hiltViewModel<AndroidCharacterCreatorViewModel>()
            val state by viewModel.state.collectAsState()
            CharacterCreatorScreen(
                state = state,
                onEvent = {event ->
                    viewModel.onEvent(event)
                }
            )
        }

    }
}
