package com.example.myemployee.data.local

import app.cash.sqldelight.db.SqlDriver
import com.example.myemployee.database.EmployeeDatabase
import com.example.myemployee.database.EmployeeDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

expect class DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}

class Database(
    databaseDriverFactory: DatabaseDriverFactory
) {
    private val database = EmployeeDatabase(
        databaseDriverFactory.createDriver()
    )
    private val query = database.appDatabaseQueries


    fun getAllEmployees(): Flow<List<EmployeeDb>> {
        return flow {
            query.getAllEmployees()
                .executeAsList().let {
                    emit(it)
                }
        }
    }

    fun insertAllEmployee(employees: List<EmployeeDb>) {
        query.transaction {
            employees.forEach { empl ->
                query.refresh(
                    id = empl.id,
                    firstName = empl.firstName,
                    lastName = empl.lastName,
                    email = empl.email,
                    phone = empl.phone,
                    department = empl.department,
                    position = empl.position,
                    image = empl.image
                )
            }
        }
    }

    fun clearAllEmployee() {
        query.clearAllEmployee()
    }
}
