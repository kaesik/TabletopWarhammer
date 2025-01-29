package com.kaesik.tabletopwarhammer.core.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.kaesik.tabletopwarhammer.database.TabletopWarhammerDatabase

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun create(): SqlDriver {
        return AndroidSqliteDriver(TabletopWarhammerDatabase.Schema, context, "character_sheet.db")
    }
}
