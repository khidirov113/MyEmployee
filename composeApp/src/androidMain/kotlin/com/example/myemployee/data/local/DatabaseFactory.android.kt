package com.example.myemployee.data.local

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.example.myemployee.database.EmployeeDatabase


actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            EmployeeDatabase.Schema,
            context,
            "MyEmployeeDatabase.db"

        )
    }
}