package com.example.myemployee.domain.usecase

import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetAllEmployee(
    private val repository: EmployeeRepository
) {
    operator fun invoke(): Flow<List<Employee>> {
        return repository.getAll()
    }
}