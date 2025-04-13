package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.AndroidCharacterSpeciesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.AndroidCharacterClassAndCareerViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.AndroidCharacterAttributesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_5skills_and_talents.AndroidCharacterSkillsAndTalentsViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_6trappings.AndroidCharacterTrappingsViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.library.presentation.library_1.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.AndroidMenuViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val string = ""

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseDriverFactory(androidApplication()) }

        viewModelOf(::AndroidMenuViewModel)

        single { string }
        viewModelOf(::AndroidLibraryViewModel)
        viewModelOf(::AndroidLibraryListViewModel)
        viewModelOf(::AndroidLibraryItemViewModel)

        viewModelOf(::AndroidCharacterSheetViewModel)

        viewModelOf(::AndroidCharacterCreatorViewModel)
        viewModelOf(::AndroidCharacterSpeciesViewModel)
        viewModelOf(::AndroidCharacterClassAndCareerViewModel)
        viewModelOf(::AndroidCharacterAttributesViewModel)
        viewModelOf(::AndroidCharacterSkillsAndTalentsViewModel)
        viewModelOf(::AndroidCharacterTrappingsViewModel)
    }
