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
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list.CharacterSheetListViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.CharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.character.SqlDelightCharacterLocalDataSource
import com.kaesik.tabletopwarhammer.core.data.library.SqlDelightLibraryLocalDataSource
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.local.SqlDelightSyncStateDataSource
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.core.data.remote.LibraryStartupSyncImpl
import com.kaesik.tabletopwarhammer.core.data.remote.SupabaseLibrarySyncManagerImpl
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.info.InfoMappers
import com.kaesik.tabletopwarhammer.core.domain.info.InfoRepository
import com.kaesik.tabletopwarhammer.core.domain.info.SimpleInfoMappers
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryLocalDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.core.domain.local.SyncStateDataSource
import com.kaesik.tabletopwarhammer.core.domain.remote.LibraryStartupSync
import com.kaesik.tabletopwarhammer.core.domain.remote.SupabaseLibrarySyncManager
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import com.kaesik.tabletopwarhammer.features.presentation.info.InfoDialogViewModel
import com.kaesik.tabletopwarhammer.library.data.LibraryClientImpl
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_1.LibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.LibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.LibraryItemViewModel
import com.kaesik.tabletopwarhammer.main.data.menu.MenuClientImpl
import com.kaesik.tabletopwarhammer.main.domain.menu.MenuClient
import com.kaesik.tabletopwarhammer.main.presentation.about.AboutViewModel
import com.kaesik.tabletopwarhammer.main.presentation.menu.MenuViewModel
import com.kaesik.tabletopwarhammer.main.presentation.settings.SettingsViewModel
import com.kaesik.tabletopwarhammer.user.data.UserClientImpl
import com.kaesik.tabletopwarhammer.user.domain.UserClient
import com.kaesik.tabletopwarhammer.user.presentation.UserViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val stringKoin: String = ""
var libraryList = listOf<LibraryItem>()
val libraryItem: LibraryItem = AttributeItem("", "", "", "", 0)

expect val platformModule: Module

val sharedModule = module {
    // Core
    single { stringKoin }
    single { HttpClientFactory.create(get()) }
    single { get<DatabaseDriverFactory>().create() }
    single { TabletopWarhammerDatabase(get()) }
    single<CharacterLocalDataSource> { SqlDelightCharacterLocalDataSource(get()) }
    single<LibraryLocalDataSource> { SqlDelightLibraryLocalDataSource(get()) }

    single<SyncStateDataSource> { SqlDelightSyncStateDataSource(get()) }

    single<SupabaseLibrarySyncManager> {
        SupabaseLibrarySyncManagerImpl(
            libraryClient = get(),
            library = get(),
            syncState = get()
        )
    }

    single<LibraryStartupSync> {
        LibraryStartupSyncImpl(
            local = get(),
            remoteSync = get()
        )
    }

    //Features
    // //Info
    single<InfoMappers> { SimpleInfoMappers() }
    single { InfoRepository(library = get(), mappers = get()) }
    viewModelOf(::InfoDialogViewModel)

    // Auth
    single<AuthClient> { AuthClientImpl() }
    single<PatternValidator> { EmailPatternValidator }
    single { UserDataValidator(get()) }
    viewModelOf(::IntroViewModel)
    viewModel {
        LoginViewModel(
            client = get(),
            authManager = get(),
            userDataValidator = get()
        )
    }
    viewModelOf(::RegisterViewModel)

    // User
    single<UserClient> { UserClientImpl() }
    viewModelOf(::UserViewModel)

    // Main
    single<MenuClient> { MenuClientImpl() }
    viewModelOf(::MenuViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::AboutViewModel)

    // Library
    single { libraryList }
    single { libraryItem }
    single<LibraryClient> {
        LibraryClientImpl(
            local = get(),
        )
    }
    viewModel { (fromTable: String) -> LibraryViewModel() }
    viewModelOf(::LibraryViewModel)
    viewModelOf(::LibraryListViewModel)
    viewModelOf(::LibraryItemViewModel)

    // Character Sheet
    viewModelOf(::CharacterSheetListViewModel)
    viewModelOf(::CharacterSheetViewModel)

    // Character Creator
    single<CharacterCreatorClient> {
        CharacterCreatorClientImpl(
            local = get(),
        )
    }
    viewModelOf(::CharacterCreatorViewModel)
    viewModelOf(::CharacterSpeciesViewModel)
    viewModelOf(::CharacterClassAndCareerViewModel)
    viewModelOf(::CharacterAttributesViewModel)
    viewModelOf(::CharacterSkillsAndTalentsViewModel)
    viewModelOf(::CharacterTrappingsViewModel)
    viewModelOf(::CharacterDetailsViewModel)
    viewModelOf(::CharacterFinalViewModel)

}
