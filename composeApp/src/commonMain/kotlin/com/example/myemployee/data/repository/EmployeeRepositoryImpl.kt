package com.example.myemployee.data.repository

import com.example.myemployee.data.local.Database
import com.example.myemployee.data.mapper.toDbModel
import com.example.myemployee.data.mapper.toDomain
import com.example.myemployee.data.remote.ApiService
import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.error.AppException
import com.example.myemployee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EmployeeRepositoryImpl(
    private val apiService: ApiService,
    private val database: Database
) : EmployeeRepository {

    override suspend fun refresh() {
        try {
            val response = apiService.getAll()
            val dbModels = response.results.map {
                it.toDbModel()
            }
            database.clearAllEmployee()
            database.insertAllEmployee(dbModels)
            println("Refresh successful with ${dbModels.size} employees")
        } catch (e: Exception) {
            throw AppException.NetworkException()
        }
    }

    override fun getAll(): Flow<List<Employee>> {
        return database.getAllEmployees().map { list ->
            println("getAllEmployees successful with ${list.size} employees")
            list.map {
                it.toDomain()
            }
        }
    }

    override suspend fun getEmployeeById(id: String): Employee {
        return try {
            val dbModel = database.getEmployeeById(id)
            dbModel.toDomain()
        } catch (e: Exception) {
            throw AppException.NetworkException()
        }
    }
}