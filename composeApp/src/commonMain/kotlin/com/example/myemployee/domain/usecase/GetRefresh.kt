package com.example.myemployee.domain.usecase

import com.example.myemployee.domain.repository.EmployeeRepository

class GetRefresh(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke() {
        repository.refresh()
    }
}