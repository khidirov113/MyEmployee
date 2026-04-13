package com.example.myemployee.presentation

import com.example.myemployee.domain.entity.Employee

data class EmployeeListState(
    val employees: List<Employee> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
