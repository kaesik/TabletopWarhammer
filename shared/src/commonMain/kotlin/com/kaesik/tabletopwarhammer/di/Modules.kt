package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.auth.data.AuthClientImpl
import com.kaesik.tabletopwarhammer.auth.data.di.EmailPatternValidator
import com.kaesik.tabletopwarhammer.auth.domain.AuthClient
import com.kaesik.tabletopwarhammer.auth.domain.di.PatternValidator
import com.kaesik.tabletopwarhammer.auth.domain.di.UserDataValidator
import com.kaesik.tabletopwarhammer.auth.presentation.intro.IntroViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.login.LoginViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.register.RegisterViewModel
import com.kaesik.tabletopwarhammer.character_creator.data.CharacterCreatorClientImpl
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final.CharacterFinalViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.CharacterSpeciesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.CharacterClassAndCareerViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.CharacterAttributesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.CharacterSkillsAndTalentsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.CharacterTrappingsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.CharacterDetailsViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet.CharacterSheetViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_sheet_list.CharacterSheetListViewModel
import com.kaesik.tabletopwarhammer.core.data.character.SqlDelightCharacterDataSource
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import com.kaesik.tabletopwarhammer.library.data.library.LibraryClientImpl
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_1.LibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.LibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.LibraryItemViewModel
import com.kaesik.tabletopwarhammer.menu.data.MenuClientImpl
import com.kaesik.tabletopwarhammer.menu.domain.MenuClient
import com.kaesik.tabletopwarhammer.menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val stringKoin: String = ""
var libraryList = listOf<LibraryItem>()
val libraryItem: LibraryItem = AttributeItem("", "", "", "", 0)

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<DatabaseDriverFactory>().create() }
    single { TabletopWarhammerDatabase(get()) }
    single<CharacterDataSource> { SqlDelightCharacterDataSource(get()) }

    single<PatternValidator> { EmailPatternValidator }
    single { UserDataValidator(get()) }

    single { stringKoin }

    // Auth
    single<AuthClient> { AuthClientImpl() }
    viewModelOf(::IntroViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::RegisterViewModel)

    // Menu
    single<MenuClient> { MenuClientImpl() }
    viewModelOf(::MenuViewModel)

    // Library
    single { libraryList }
    single { libraryItem }
    single<LibraryClient> { LibraryClientImpl() }
    viewModel { (fromTable: String) -> LibraryViewModel() }
    viewModelOf(::LibraryViewModel)
    viewModelOf(::LibraryListViewModel)
    viewModelOf(::LibraryItemViewModel)

    // Character Sheet
    viewModelOf(::CharacterSheetListViewModel)
    viewModelOf(::CharacterSheetViewModel)

    // Character Creator
    single<CharacterCreatorClient> { CharacterCreatorClientImpl() }
    single { CharacterCreatorViewModel() }
    viewModelOf(::CharacterSpeciesViewModel)
    viewModelOf(::CharacterClassAndCareerViewModel)
    viewModelOf(::CharacterAttributesViewModel)
    viewModelOf(::CharacterSkillsAndTalentsViewModel)
    viewModelOf(::CharacterTrappingsViewModel)
    viewModelOf(::CharacterDetailsViewModel)
    viewModelOf(::CharacterFinalViewModel)

}
