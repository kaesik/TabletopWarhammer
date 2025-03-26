package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.character_creator.presentation.AndroidCharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.AndroidCharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.library.presentation.library.AndroidLibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_item.AndroidLibraryItemViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_list.AndroidLibraryListViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.AndroidMenuViewModel
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseDriverFactory(androidApplication()) }

        viewModelOf(::AndroidMenuViewModel)

        viewModelOf(::AndroidLibraryViewModel)
        viewModelOf(::AndroidLibraryListViewModel)
        viewModelOf(::AndroidLibraryItemViewModel)

        viewModelOf(::AndroidCharacterSheetViewModel)

        viewModelOf(::AndroidCharacterCreatorViewModel)
    }
