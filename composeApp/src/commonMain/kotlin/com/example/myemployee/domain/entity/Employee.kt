package com.example.myemployee.domain.entity

data class Employee(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val department: String,
    val position: String,
    val image: String?
)