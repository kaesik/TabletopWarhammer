package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.character_creator.presentation.CharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListViewModel
import com.kaesik.tabletopwarhammer.menu.presentation.MenuViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

const val stringKoin: String = ""
val libraryList = listOf<LibraryItem>()
val libraryItem: LibraryItem = AttributeItem("", "", "", "", 0)

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single { get<DatabaseDriverFactory>().create() }

    viewModelOf(::MenuViewModel)

    single { stringKoin }
    single { libraryList }
    single { libraryItem }
    viewModelOf(::LibraryViewModel)
    viewModelOf(::LibraryListViewModel)
    viewModelOf(::LibraryItemViewModel)

    viewModelOf(::CharacterSheetViewModel)
    viewModelOf(::CharacterCreatorViewModel)
}
