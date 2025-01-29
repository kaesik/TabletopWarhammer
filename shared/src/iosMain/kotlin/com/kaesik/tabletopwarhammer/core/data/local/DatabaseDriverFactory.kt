package com.kaesik.tabletopwarhammer.core.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory {
    actual fun create(): SqlDriver {
        return NativeSqliteDriver(TabletopWarhammerDatabase.Schema, "character_sheet.db")
    }
}
