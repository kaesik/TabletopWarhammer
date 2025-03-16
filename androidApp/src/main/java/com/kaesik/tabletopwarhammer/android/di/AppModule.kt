package com.kaesik.tabletopwarhammer.android.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import com.kaesik.tabletopwarhammer.core.data.local.DatabaseDriverFactory
import com.kaesik.tabletopwarhammer.core.data.remote.HttpClientFactory
import com.kaesik.tabletopwarhammer.library.data.library.KtorLibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.Library
import com.kaesik.tabletopwarhammer.library.domain.library.LibraryClient
import com.kaesik.tabletopwarhammer.library.domain.library.items.AttributeItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.CareerPathItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ClassItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.ItemItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.LibraryItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.QualityFlawItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SkillItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.SpeciesItem
import com.kaesik.tabletopwarhammer.library.domain.library.items.TalentItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClientFactory().create()
    }

    @Provides
    @Singleton
    fun provideDatabaseDriverFactory(app: Application): SqlDriver {
        return DatabaseDriverFactory(app).create()
    }

    @Provides
    @Singleton
    fun provideLibraryClient(): LibraryClient {
        return KtorLibraryClient()
    }

    @Provides
    @Singleton
    fun provideLibraryUseCase(
        client: LibraryClient,
    ): Library {
        return Library(client)
    }

    @Provides
    @Singleton
    fun provideLibraryId(): String {
        return ""
    }

    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideLibraryList(): List<LibraryItem> {
        return emptyList()
    }
}
