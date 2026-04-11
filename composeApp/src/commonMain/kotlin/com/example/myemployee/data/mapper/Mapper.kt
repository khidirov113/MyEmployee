package com.example.myemployee.data.mapper

import com.example.myemployee.data.remote.EmployeeResponseDto
import com.example.myemployee.database.EmployeeDb
import com.example.myemployee.domain.entity.Employee

private val department = listOf(
    "IT", "HR", "Sales", "Marketing", "Finance"
)

private val positions = listOf(
    "Android Developer",
    "Backend Developer",
    "Frontend Developer",
    "HR Manager",
    "Manager",
    "QA Engineer"
)

fun EmployeeResponseDto.toDbModel(): EmployeeDb {
    return EmployeeDb(
        id = login.id,
        firstName = name.firstName,
        lastName = name.lastName,
        email = email,
        phone = phone,
        department = department.random(),
        position = positions.random(),
        image = image.image
    )
}

fun EmployeeDb.toDomain(): Employee {
    return Employee(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        department = department,
        position = position,
        image = image
    )
}