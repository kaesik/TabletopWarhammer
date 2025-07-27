package com.kaesik.tabletopwarhammer.android.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kaesik.tabletopwarhammer.auth.presentation.intro.AndroidIntroViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.intro.IntroScreenRoot
import com.kaesik.tabletopwarhammer.auth.presentation.login.AndroidLoginViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.login.LoginScreenRoot
import com.kaesik.tabletopwarhammer.auth.presentation.register.AndroidRegisterViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.register.RegisterScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final.AndroidCharacterFinalViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final.CharacterFinalScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.AndroidCharacterSpeciesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.CharacterSpeciesScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.AndroidCharacterClassAndCareerViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.CharacterClassAndCareerScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.AndroidCharacterAttributesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.CharacterAttributesScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.AndroidCharacterSkillsAndTalentsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.CharacterSkillsAndTalentsScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.AndroidCharacterTrappingsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.CharacterTrappingsScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.AndroidCharacterDetailsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.CharacterDetailsScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question.AndroidCharacterTenQuestionsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_8ten_question.CharacterTenQuestionsScreenRoot
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_9advancement.AndroidCharacterAdvancementViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_9advancement.CharacterAdvancementScreenRoot
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list.AndroidCharacterSheetListViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list.CharacterSheetListScreenRoot
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetScreenRoot
import com.kaesik.tabletopwarhammer.core.domain.Route
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.presentation.di.LocalNavController
import com.kaesik.tabletopwarhammer.library.presentation.library_1.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_1.LibraryScreenRoot
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.LibraryListScreenRoot
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.LibraryItemScreenRoot
import com.kaesik.tabletopwarhammer.main.presentation.about.AboutScreenRoot
import com.kaesik.tabletopwarhammer.main.presentation.about.AndroidAboutViewModel
import com.kaesik.tabletopwarhammer.main.presentation.menu.AndroidMenuViewModel
import com.kaesik.tabletopwarhammer.main.presentation.menu.MenuScreenRoot
import com.kaesik.tabletopwarhammer.main.presentation.settings.AndroidSettingsViewModel
import com.kaesik.tabletopwarhammer.main.presentation.settings.SettingsScreenRoot
import com.kaesik.tabletopwarhammer.user.presentation.AndroidUserViewModel
import com.kaesik.tabletopwarhammer.user.presentation.UserScreenRoot
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.getKoin

@Composable
fun App() {
    val navController = rememberNavController()
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = Route.AuthGraph
        ) {
            navigation<Route.AuthGraph>(
                startDestination = Route.Intro
            ) {
                composable<Route.Intro> {
                    val viewModel = koinViewModel<AndroidIntroViewModel>()
                    IntroScreenRoot(
                        viewModel = viewModel,
                        onSignUpClick = {
                            navController.navigate(
                                Route.Register
                            )
                        },
                        onSignInClick = {
                            navController.navigate(
                                Route.Login
                            )
                        },
                        onGuestClick = {
                            navController.navigate(
                                Route.MainGraph
                            )
                        },
                    )
                }
                composable<Route.Login> {
                    val viewModel = koinViewModel<AndroidLoginViewModel>()
                    LoginScreenRoot(
                        viewModel = viewModel,
                        onLoginSuccess = {
                            navController.navigate(
                                Route.MainGraph
                            )
                        },
                        onSignUpClick = {
                            navController.navigate(
                                Route.Register
                            )
                        },
                        onGuestClick = {
                            navController.navigate(
                                Route.MainGraph
                            )
                        },
                    )
                }
                composable<Route.Register> {
                    val viewModel = koinViewModel<AndroidRegisterViewModel>()
                    RegisterScreenRoot(
                        viewModel = viewModel,
                        onSignInClick = {
                            navController.navigate(
                                Route.Login
                            )
                        },
                        onSuccessfulRegistration = {
                            navController.navigate(
                                Route.Login
                            )
                        },
                    )
                }
            }
            navigation<Route.MainGraph>(
                startDestination = Route.Menu
            ) {
                composable<Route.Menu> {
                    val viewModel = koinViewModel<AndroidMenuViewModel>()
                    MenuScreenRoot(
                        viewModel = viewModel,
                        onNavigateToLibraryScreen = {
                            navController.navigate(
                                Route.LibraryGraph
                            )
                        },
                        onNavigateToCharacterSheetScreen = {
                            navController.navigate(
                                Route.CharacterSheetGraph
                            )
                        },
                        onNavigateToCharacterCreatorScreen = {
                            navController.navigate(
                                Route.CharacterCreatorGraph
                            )
                        },
                    )
                }
                composable<Route.Settings> {
                    val viewModel = koinViewModel<AndroidSettingsViewModel>()
                    SettingsScreenRoot(
                        viewModel = viewModel,
                    )
                }
                composable<Route.About> {
                    val viewModel = koinViewModel<AndroidAboutViewModel>()
                    AboutScreenRoot(
                        viewModel = viewModel,
                    )
                }
            }
            navigation<Route.UserGraph>(
                startDestination = Route.User
            ) {
                composable<Route.User> {
                    val viewModel = koinViewModel<AndroidUserViewModel>()
                    UserScreenRoot(
                        viewModel = viewModel
                    )
                }
            }
            navigation<Route.LibraryGraph>(
                startDestination = Route.Library
            ) {
                composable<Route.Library> {
                    val viewModel = koinViewModel<AndroidLibraryViewModel>()
                    LibraryScreenRoot(
                        viewModel = viewModel,
                        onLibraryListSelect = {
                            println("App:LibraryScreenRoot $it")
                            navController.navigate(
                                Route.LibraryList(fromTable = it)
                            )
                        }
                    )
                }
                composable<Route.LibraryList> { backstackEntry ->
                    val viewModel = koinViewModel<AndroidLibraryListViewModel>()
                    val libraryListRoute: Route.LibraryList = backstackEntry.toRoute()
//                main()
//                println("main() $it")
                    LibraryListScreenRoot(
                        viewModel = viewModel,
                        fromTable = libraryListRoute.fromTable,
                        onLibraryItemSelect = {
                            println("App:LibraryListScreenRoot $it")
                            navController.navigate(
                                Route.LibraryItem(
                                    itemId = it,
                                    fromTable = libraryListRoute.fromTable
                                )
                            )
                        }
                    )
                }
                composable<Route.LibraryItem> { backstackEntry ->
                    val viewModel = koinViewModel<AndroidLibraryItemViewModel>()
                    val libraryItemRoute: Route.LibraryItem = backstackEntry.toRoute()
                    LibraryItemScreenRoot(
                        viewModel = viewModel,
                        itemId = libraryItemRoute.itemId,
                        fromTable = libraryItemRoute.fromTable
                    )
                }
            }
            navigation<Route.CharacterSheetGraph>(
                startDestination = Route.CharacterSheetList
            ) {
                composable<Route.CharacterSheetList> {
                    val viewModel = koinViewModel<AndroidCharacterSheetListViewModel>()
                    CharacterSheetListScreenRoot(
                        viewModel = viewModel,
                        onCharacterClick = {
                            navController.navigate(
                                Route.CharacterSheet(
                                    characterId = it.id
                                )
                            )
                        }
                    )
                }
                composable<Route.CharacterSheet> { backstackEntry ->
                    val viewModel = koinViewModel<AndroidCharacterSheetViewModel>()
                    val characterSheetRoute: Route.CharacterSheet = backstackEntry.toRoute()
                    CharacterSheetScreenRoot(
                        viewModel = viewModel,
                        characterId = characterSheetRoute.characterId
                    )
                }
            }
            navigation<Route.CharacterCreatorGraph>(
                startDestination = Route.CharacterCreator
            ) {
                composable<Route.CharacterCreator> {
                    val viewModel = koinViewModel<AndroidCharacterCreatorViewModel>()
                    CharacterCreatorScreenRoot(
                        viewModel = viewModel,
                        onCreateCharacterSelect = {
                            navController.navigate(
                                Route.CharacterSpecies
                            )
                        },
                        onRandomCharacterSelect = {
                            navController.navigate(
                                Route.CharacterFinal
                            )
                        }
                    )
                }
                composable<Route.CharacterSpecies> {
                    val viewModel = koinViewModel<AndroidCharacterSpeciesViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterSpeciesScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        onSpeciesSelect = { speciesItem ->
                            println("Selected species: ${speciesItem.name}")
                        },
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterClassAndCareer(
                                    characterSpecies = creatorViewModel.state.value.character.species
                                )
                            )
                        }
                    )
                }
                composable<Route.CharacterClassAndCareer> {
                    val viewModel = koinViewModel<AndroidCharacterClassAndCareerViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterClassAndCareerScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        speciesName = creatorViewModel.state.value.character.species,
                        onCareerSelect = { careerItem ->
                            println("Selected career: ${careerItem.name}")
                        },
                        onClassSelect = { classItem ->
                            println("Selected class: ${classItem.name}")
                        },
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterAttributes(
                                    characterSpecies = creatorViewModel.state.value.character.species
                                )
                            )
                        }
                    )
                }
                composable<Route.CharacterAttributes> {
                    val viewModel = koinViewModel<AndroidCharacterAttributesViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterAttributesScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterSkillsAndTalents(
                                    characterSpecies = creatorViewModel.state.value.character.species,
                                    characterCareer = creatorViewModel.state.value.character.career
                                )
                            )
                        },
                        characterSpecies = creatorViewModel.state.value.character.species,
                    )
                }
                composable<Route.CharacterSkillsAndTalents> {
                    val viewModel = koinViewModel<AndroidCharacterSkillsAndTalentsViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterSkillsAndTalentsScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterTrappings(
                                    characterClass = creatorViewModel.state.value.character.cLass,
                                    characterCareerPath = creatorViewModel.state.value.character.careerPath,
                                )
                            )
                        }
                    )
                }
                composable<Route.CharacterTrappings> {
                    val viewModel = koinViewModel<AndroidCharacterTrappingsViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()
                    CharacterTrappingsScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterDetails(
                                    characterSpecies = creatorViewModel.state.value.character.species,
                                )
                            )
                        }
                    )
                }
                composable<Route.CharacterDetails> {
                    val viewModel = koinViewModel<AndroidCharacterDetailsViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()
                    CharacterDetailsScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        onNextClick = {
                            navController.navigate(
                                Route.CharacterFinal
                            )
                        }
                    )
                }
                composable<Route.CharacterTenQuestions> {
                    val viewModel = koinViewModel<AndroidCharacterTenQuestionsViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterTenQuestionsScreenRoot(
                        viewModel = viewModel,
                    )
                }
                composable<Route.CharacterAdvancement> {
                    val viewModel = koinViewModel<AndroidCharacterAdvancementViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()

                    CharacterAdvancementScreenRoot(
                        viewModel = viewModel,
                    )
                }
                composable<Route.CharacterFinal> {
                    val viewModel = koinViewModel<AndroidCharacterFinalViewModel>()
                    val creatorViewModel = getKoin().get<AndroidCharacterCreatorViewModel>()
                    val characterDataSource = getKoin().get<CharacterDataSource>()

                    CharacterFinalScreenRoot(
                        viewModel = viewModel,
                        creatorViewModel = creatorViewModel,
                        characterDataSource = characterDataSource,
                        onSaveClick = {
                            navController.navigate(Route.MainGraph) {
                                popUpTo(Route.CharacterCreatorGraph) {
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}
