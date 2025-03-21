package com.kaesik.tabletopwarhammer.di

import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.library.presentation.library.LibraryViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_item.LibraryItemViewModel
import com.kaesik.tabletopwarhammer.library.presentation.library_list.LibraryListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    single {
        get<DatabaseDriverFactory>().create()
    }

    viewModelOf(::LibraryViewModel)
    viewModelOf(::LibraryListViewModel)
    viewModelOf(::LibraryItemViewModel)
}
