package com.example.myemployee.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import com.example.myemployee.database.EmployeeDatabase
import com.example.myemployee.database.EmployeeDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow

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
        return query.getAllEmployees().asFlow().mapToList(Dispatchers.IO)
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

    fun getEmployeeById(id: String): EmployeeDb {
        return query.getEmployeeById(id = id).executeAsOne()
    }

    fun searchEmployee(text: String): Flow<List<EmployeeDb>> {
        return query.searchEmployees(query = text).asFlow().mapToList(Dispatchers.IO)
    }

    fun clearAllEmployee() {
        query.clearAllEmployee()
    }
}
