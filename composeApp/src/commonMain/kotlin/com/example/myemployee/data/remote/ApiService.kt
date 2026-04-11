package com.example.myemployee.data.remote

interface ApiService {
    suspend fun getAll(
        results: Int = 50,
        include: String = "name,email,login,phone,picture"
    ): EmployeeListResponseDto
}