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
    @IntoSet
    fun provideAttributeItem(): LibraryItem {
        return AttributeItem(
            "1",
            "Siła",
            "Opis siły",
            "STR",
            10
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideCareerItem(): LibraryItem {
        return CareerItem(
            "2",
            "Żołnierz",
            "Brak ograniczeń",
            "Opis kariery",
            "Scheme",
            "Cytat",
            "Przygody",
            "Źródło",
            "Ścieżka kariery",
            "Wojownik",
            15
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideCareerPathItem(): LibraryItem {
        return CareerPathItem(
            "3",
            "Piechur",
            "Status",
            "Umiejętności",
            "Ekwipunek",
            "Talenty"
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideClassItem(): LibraryItem {
        return  ClassItem(
            "4",
            "Wojownik",
            "Opis klasy",
            "Broń, zbroja",
            "Żołnierz, Rycerz",
            20
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideItemItem(): LibraryItem {
        return  ItemItem(
            "5",
            "Miecz",
            "Broń",
            "Źródło",
            "2",
            "Powszechna",
            "Tak",
            "+4",
            "Opis miecza",
            "1",
            false,
            "Ręce",
            "Brak",
            "10 zk",
            "Jakościowe",
            "1",
            "1m",
            "Walka wręcz",
            "Broń",
            30
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideQualityFlawItem(): LibraryItem {
        return QualityFlawItem(
            "6",
            "Ostry",
            "Broń",
            "Zwiększa obrażenia",
            true,
            "Źródło",
            35
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideSkillItem(): LibraryItem {
        return SkillItem(
            "7",
            "Wspinaczka",
            "Siła",
            true,
            false,
            "Opis wspinaczki",
            "Ściany",
            "Źródło",
            40
        )
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideSpeciesItem(): LibraryItem {
        return SpeciesItem(
            "8",
            "Człowiek",
            "Opis rasy",
            "Opinie",
            "Źródło",
            "30",
            "25",
            "30",
            "30",
            "30",
            "30",
            "30",
            "30",
            "30",
            "10",
            "1",
            "1",
            "1",
            "4",
            "Wspinaczka, Skakanie",
            "Silny cios",
            "Jan",
            "Kowalski",
            "Brak",
            "Nieustraszony",
            "20-30",
            "Niebieskie",
            "Brązowe",
            "175 cm",
            "30",
            45,
            "Imiona")
    }

    @Provides
    @Singleton
    @IntoSet
    fun provideTalentItem(): LibraryItem {
        return TalentItem(
            "9",
            "Silny Cios",
            "3",
            "Testy siły",
            "Opis talentu",
            "Źródło",
            50
        )

    }

    @Provides
    @Singleton
    @JvmSuppressWildcards
    fun provideLibraryList(
        items: Set<LibraryItem>
    ): List<LibraryItem> {
        return items.toList()
    }
}
