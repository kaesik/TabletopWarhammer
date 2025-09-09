package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.auth.domain.AuthManager
import com.kaesik.tabletopwarhammer.auth.presentation.intro.AndroidIntroViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.login.AndroidLoginViewModel
import com.kaesik.tabletopwarhammer.auth.presentation.register.AndroidRegisterViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_10final.AndroidCharacterFinalViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.AndroidCharacterSpeciesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.AndroidCharacterClassAndCareerViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.AndroidCharacterAttributesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.AndroidCharacterSkillsAndTalentsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.AndroidCharacterTrappingsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_7details.AndroidCharacterDetailsViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_1sheet_list.AndroidCharacterSheetListViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.character_2sheet.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.character.SqlDelightCharacterDataSource
import com.kaesik.tabletopwarhammer.core.data.library.SqlDelightLibraryDataSource
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.domain.character.CharacterDataSource
import com.kaesik.tabletopwarhammer.core.domain.library.LibraryDataSource
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase
import com.kaesik.tabletopwarhammer.features.info.AndroidInfoDialogViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_1.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.main.presentation.about.AndroidAboutViewModel
import com.kaesik.tabletopwarhammer.main.presentation.menu.AndroidMenuViewModel
import com.kaesik.tabletopwarhammer.main.presentation.settings.AndroidSettingsViewModel
import com.kaesik.tabletopwarhammer.user.presentation.AndroidUserViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseDriverFactory(androidApplication()) }
        single { TabletopWarhammerDatabase(get()) }
        single<CharacterDataSource> { SqlDelightCharacterDataSource(get()) }
        single<LibraryDataSource> { SqlDelightLibraryDataSource(get()) }

        //Features
        // //Info
        viewModelOf(::AndroidInfoDialogViewModel)

        // Auth
        single { AuthManager(androidContext()) }
        viewModelOf(::AndroidIntroViewModel)
        viewModelOf(::AndroidLoginViewModel)
        viewModelOf(::AndroidRegisterViewModel)

        //User
        viewModelOf(::AndroidUserViewModel)

        // Main
        viewModel {
            AndroidMenuViewModel(
                client = get(),
                librarySyncManager = get()
            )
        }
        viewModelOf(::AndroidSettingsViewModel)
        viewModelOf(::AndroidAboutViewModel)

        // Library
        viewModelOf(::AndroidLibraryViewModel)
        viewModelOf(::AndroidLibraryListViewModel)
        viewModelOf(::AndroidLibraryItemViewModel)

        // Character Sheet
        viewModelOf(::AndroidCharacterSheetListViewModel)
        viewModelOf(::AndroidCharacterSheetViewModel)

        // Character Creator
        single { AndroidCharacterCreatorViewModel() }
        viewModelOf(::AndroidCharacterSpeciesViewModel)
        viewModelOf(::AndroidCharacterClassAndCareerViewModel)
        viewModelOf(::AndroidCharacterAttributesViewModel)
        viewModelOf(::AndroidCharacterSkillsAndTalentsViewModel)
        viewModelOf(::AndroidCharacterTrappingsViewModel)
        viewModelOf(::AndroidCharacterDetailsViewModel)
        viewModelOf(::AndroidCharacterFinalViewModel)
    }
