package com.example.myemployee.domain.repository

import com.example.myemployee.domain.entity.Employee
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {

    suspend fun refresh()
    fun getAll(): Flow<List<Employee>>

    suspend fun getEmployeeById(id: String): Employee

    fun searchEmployee(query: String): Flow<List<Employee>>

}