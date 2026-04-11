package com.example.myemployee.data.local

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.example.myemployee.database.EmployeeDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(
            EmployeeDatabase.Schema,
            "MyEmployeeDatabase.db"
        )
    }
}