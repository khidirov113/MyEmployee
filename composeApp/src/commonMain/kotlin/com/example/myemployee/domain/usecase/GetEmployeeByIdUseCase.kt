package com.example.myemployee.domain.usecase

import com.example.myemployee.domain.entity.Employee
import com.example.myemployee.domain.repository.EmployeeRepository

class GetEmployeeByIdUseCase(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(id: String): Employee{
        return repository.getEmployeeById(id)
    }
}