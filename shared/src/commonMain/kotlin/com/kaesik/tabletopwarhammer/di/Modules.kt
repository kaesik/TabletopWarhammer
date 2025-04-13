package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.character_creator.data.KtorCharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.domain.CharacterCreatorClient
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_1creator.CharacterCreatorViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_2species.CharacterSpeciesViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_3class_and_career.CharacterClassAndCareerViewModel
import com.kaesik.tabletopwarhammer.character_creator.presentation.character_4attributes.CharacterAttributesViewModel
import com.kaesik.tabletopwarhammer.character_sheet.presentation.CharacterSheetViewModel
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.library.data.library.KtorLibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.presentation.library_1.LibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_2list.LibraryListViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_3item.LibraryItemViewModel
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

    viewModelOf(::MenuViewModel)

    single { stringKoin }
    single { libraryList }
    single { libraryItem }
    single { KtorLibraryClient() as LibraryClient }
    viewModel { (fromTable: String) -> LibraryViewModel() }
    viewModelOf(::LibraryViewModel)
    viewModelOf(::LibraryListViewModel)
    viewModelOf(::LibraryItemViewModel)

    viewModelOf(::CharacterSheetViewModel)

    single { KtorCharacterCreatorClient() as CharacterCreatorClient }
    viewModelOf(::CharacterCreatorViewModel)
    viewModelOf(::CharacterSpeciesViewModel)
    viewModelOf(::CharacterClassAndCareerViewModel)
    viewModelOf(::CharacterAttributesViewModel)
}
