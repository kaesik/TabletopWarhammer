package com.kaesik.tabletopwarhammer.android.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.kaesik.tabletopwarhammer.character_creator.presentation.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorScreenRoot
import com.kaesik.tabletopwarhammer.character_sheet.presentation.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetScreenRoot
import com.kaesik.tabletopwarhammer.library.presentation.library.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryScreenRoot
import com.kaesik.tabletopwarhammer.library.presentation.library_item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemScreenRoot
import com.kaesik.tabletopwarhammer.library.presentation.library_list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListScreenRoot
import com.kaesik.tabletopwarhammer.menu.presentation.AndroidMenuViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.MenuScreenRoot
import org.koin.androidx.compose.koinViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.MainGraph
    ) {
        navigation<Route.MainGraph>(
            startDestination = Route.Menu
        ) {
            composable<Route.Menu> {
                val viewModel = koinViewModel<AndroidMenuViewModel>()
                MenuScreenRoot(
                    viewModel = viewModel,
                    onNavigateToLibraryScreen = {
                        navController.navigate(
                            Route.Library
                        )
                    },
                    onNavigateToCharacterSheetScreen = {
                        navController.navigate(
                            Route.CharacterSheet
                        )
                    },
                    onNavigateToCharacterCreatorScreen = {
                        navController.navigate(
                            Route.CharacterCreator
                        )
                    }
                )
            }
            composable<Route.Library> {
                val viewModel = koinViewModel<AndroidLibraryViewModel>()
                LibraryScreenRoot(
                    viewModel = viewModel,
                    onLibrarySelect = {
                        navController.navigate(
                            Route.LibraryList
                        )
                    }
                )
            }
            composable<Route.LibraryList> {
                val viewModel = koinViewModel<AndroidLibraryListViewModel>()
                LibraryListScreenRoot(
                    viewModel = viewModel,
                    onLibraryItemSelect = {
                        navController.navigate(
                            Route.LibraryItem(it.id)
                        )
                    }
                )
            }
            composable<Route.LibraryItem> {
                val viewModel = koinViewModel<AndroidLibraryItemViewModel>()
                LibraryItemScreenRoot(
                    viewModel = viewModel,
                    onBackClick = {
                        navController.navigateUp()
                    },
                    onFavoriteClick = {

                    }
                )
            }
            composable<Route.CharacterSheet> {
                val viewModel = koinViewModel<AndroidCharacterSheetViewModel>()
                CharacterSheetScreenRoot(
                    viewModel = viewModel,
                )
            }
            composable<Route.CharacterCreator> {
                val viewModel = koinViewModel<AndroidCharacterCreatorViewModel>()
                CharacterCreatorScreenRoot(
                    viewModel = viewModel,
                )
            }
        }
    }
}
